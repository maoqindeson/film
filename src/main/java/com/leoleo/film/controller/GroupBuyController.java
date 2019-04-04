package com.leoleo.film.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.leoleo.film.entity.Goods;
import com.leoleo.film.entity.GroupBuy;
import com.leoleo.film.entity.Order;
import com.leoleo.film.entity.User;
import com.leoleo.film.service.GoodsService;
import com.leoleo.film.service.GroupBuyService;
import com.leoleo.film.service.OrderService;
import com.leoleo.film.service.UserService;
import com.leoleo.film.utils.JWTUtil;
import com.leoleo.film.utils.MaoqinObject;
import com.leoleo.film.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groupBuy")
public class GroupBuyController {
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

//    @PostMapping("/getGroupBuyByProductId")
//    public GroupBuy getGroupBuyByProductId(String productId) {
//        GroupBuy groupBuy = groupBuyService.getGroupBuyByProductId(productId);
//        return groupBuy;
//    }

    @PostMapping("/insertGroupBuy")
    @RequiresAuthentication
    public MaoqinObject insertGroupBuy(String productId, BigDecimal price, Integer miniNum, Date endDate, Date createdDate) {
        GroupBuy groupBuy = new GroupBuy();
        if (price == null || price.compareTo(new BigDecimal(0)) <= 0) {
            return MaoqinObject.error("不能不设置价格");
        }
        if (StringTools.isNullOrEmpty(productId)) {
            return MaoqinObject.error("不能不设置货号");
        }
        if (createdDate == null || createdDate.compareTo(new Date()) < 0) {
            return MaoqinObject.error("设置开团时间不正确");
        }
        if (endDate == null || endDate.compareTo(createdDate) < 0) {
            return MaoqinObject.error("设置团购结束时间不正确");
        }
        if (miniNum == null || miniNum <= 0) {
            return MaoqinObject.error("设置团购商品数量不正确");
        }
        groupBuy.setPrice(price);
        groupBuy.setProductId(productId);
        groupBuy.setStatus(null);
        groupBuy.setCreatedDate(createdDate);
        groupBuy.setEndDate(endDate);
        groupBuy.setMiniNum(miniNum);
        groupBuy.setUpdatedDate(null);
        int resulrt = groupBuyService.insertGroupBuy(groupBuy);
        if (resulrt == 0) {
            log.error("插入失败");
            return MaoqinObject.error("生成团购失败");
        }
        return MaoqinObject.ok(resulrt, "插入成功");
    }

    //    @PostMapping("/updateGroupBuyStatus")
//    public MaoqinObject updateGroupBuyStatus(String productId, Integer status) {
//        return MaoqinObject.ok();
//    }
//    @PostMapping("/updateBuyUpdatedDate")
//    public MaoqinObject updateGroupBuyUpdatedDate(String productId, Date updatedDate) {
//        return MaoqinObject.ok();
//    }
    @PostMapping("/inGroupBuy")
    @RequiresAuthentication
    @Transactional(rollbackFor = RuntimeException.class)
    public MaoqinObject inGroupBuy(Integer id, Integer numbers, HttpServletRequest request) {
        String username = JWTUtil.getCurrentUsername(request);
        try {
            if (id == null || id == 0) {        //校验团购号入参
                return MaoqinObject.ok(500, "团购商品id不能为空");
            }
            if (numbers == null || numbers == 0) {              //校验商品数量入参
                return MaoqinObject.ok(500, "商品数量不能为空或是0");
            }
            GroupBuy groupBuy = groupBuyService.getGroupBuyById(id);  //查询到团购对象
            if (groupBuy == null) {                               //校验是否有团购
                return MaoqinObject.error("没有团购");
            }
            if (groupBuy.getStatus() != 1) {             //校验团购状态
                return MaoqinObject.error("未开团");
            }
            if (groupBuy.getStatus() == 2 || groupBuy.getStatus() == 3) {        //校验团购状态
                return MaoqinObject.error("团购结束");
            }
            Date createdDate = groupBuy.getCreatedDate();
            Date endDate = groupBuy.getEndDate();
            Integer groupBuyID = groupBuy.getId();
            Integer num = groupBuyService.countGroupBuyNumbers(createdDate, endDate, groupBuyID);  //查询已售出的团购商品数量
            if (num + numbers > groupBuy.getMiniNum()) {
                return MaoqinObject.error("团购失败，货不够");
            }
            Order order = new Order();
            order.setGoodsid(groupBuy.getProductId());
            order.setName(username);
            order.setNumbers(numbers);
            order.setOrderid(StringTools.getTradeno());
            order.setOrderStatus("no pay");
            order.setOrderTime(new Date());
            order.setPrice(groupBuy.getPrice());
            order.setGroupBuyId(groupBuyID);
            boolean result = orderService.insert(order);
            if (!result) {
                return MaoqinObject.error("团购下单失败");
            }
            BigDecimal total = order.getPrice().multiply(new BigDecimal(numbers));
            if (order.getOrderStatus().equals("pay")) {
                return MaoqinObject.error("已支付的订单，不用重新支付");
            }
            User user = userService.getUserByName(username);
            if (user.getBalance().compareTo(total) < 0) {
                return MaoqinObject.error("余额不足，请充值");
            }
            BigDecimal balance = user.getBalance().subtract(total);
            Integer blResult = userService.updateBalance(username, balance);
            if (blResult == 0) {
                return MaoqinObject.error("支付失败，请重试");
            }
            Goods goods = goodsService.getGoodsByGoodsid(groupBuy.getProductId());
            Integer nu = goods.getNumbers() - order.getNumbers();
            Integer nuResult = goodsService.updateGoods(goods.getGoodsid(), nu);
            if (nuResult == 0) {
                log.error("更新库存失败，执行回滚");
                throw new RuntimeException();
            }
            Integer paresult = orderService.updateOrder(StringTools.getTradeno(), "pay");
            if (paresult == 0) {
                log.error("更新订单状态失败，执行回滚");
                throw new RuntimeException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return MaoqinObject.ok(500, "团购成功,等待结果");
    }

    @PostMapping("/countGroupBuyNumbers")
    public int countGroupBuyNumbers(Date createdDate, Date endDate, Integer groupBuyId) {
        return groupBuyService.countGroupBuyNumbers(createdDate, endDate, groupBuyId);
    }


}
