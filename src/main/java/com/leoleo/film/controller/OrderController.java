package com.leoleo.film.controller;


import com.leoleo.film.entity.Goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.leoleo.film.entity.Order;
import com.leoleo.film.entity.Price;
import com.leoleo.film.entity.User;
import com.leoleo.film.service.GoodsService;
import com.leoleo.film.service.OrderService;
import com.leoleo.film.service.PriceService;
import com.leoleo.film.service.UserService;
import com.leoleo.film.utils.JWTUtil;
import com.leoleo.film.utils.MaoqinObject;
import com.leoleo.film.utils.OrderGoodsName;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("buy")
    @RequiresAuthentication
    @Transactional(rollbackFor = Exception.class)
    public synchronized MaoqinObject buy(String orderid, String goodsid, int numbers, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");    //通过Header拿到用户token
        String name = JWTUtil.getUsername(token);        //解密token拿到用户名
        User user = userService.getUserByName(name);
        Price pr = priceService.getPriceByGoodsid(goodsid);
        BigDecimal price = pr.getPrice();
        if (user.getBalance().compareTo(price) < 0) {
            return MaoqinObject.ok("余额不足");
        }
        Goods go = goodsService.getGoodsByGoodsid(goodsid);
        BigDecimal total =price.multiply(new BigDecimal(numbers));
        BigDecimal balance = user.getBalance().subtract(total);
        int updateResult =userService.updateBalance(name,balance);
        if (0==updateResult ){                                                            //需要判断movieUserService.insertMovieUser结果是否成功，成功了才给用户更新余额userService.updateBalance
            return MaoqinObject.ok("减余额失败，请重试");
        }
        if (updateResult>1){
            throw new Exception();
        }
        if(go.getNumbers()<numbers){
            return MaoqinObject.ok("库存不足");
        }
        int num = go.getNumbers()-numbers;
        goodsService.updateGoods(goodsid,num);
        orderService.insertOrder(orderid,name,goodsid,price,numbers,new Date(),"pay");
          MaoqinObject maoqinObject = new MaoqinObject();
          maoqinObject.setM(1);
          maoqinObject.setMessage("购买成功");
          maoqinObject.setObject(balance);
          return maoqinObject;
    }

    @PostMapping("getOrderByOrderid")
    public Order getOrderByOrderid(String orderid) {
        Order order = orderService.getOrderByOrderid(orderid);
        return order;
    }

    @PostMapping("getOrderByPage")
    public MaoqinObject getOrderByPage(Integer pageNo, Integer pageSize) {
        int start = (pageNo - 1) * pageSize - pageSize;
        List<Order> list = orderService.getOrderByPage(start, pageSize);
        MaoqinObject maoqinObject = new MaoqinObject();
        maoqinObject.setM(1);
        maoqinObject.setMessage("sucess");
        maoqinObject.setObject(list);
        return maoqinObject;
    }

    @PostMapping("getOrderGoodsNameByOrderid")
    public List<OrderGoodsName> getOrderGoodsNameByOrderid(String orderid) {
        List<OrderGoodsName> list = orderService.getOrderGoodsNameByOrderid(orderid);
        return list;
    }

    @PostMapping("insertOrder")
    public int insertOrder(String orderid, String name, String goodsid, BigDecimal price, Integer numbers, Date orderTime, String orderStatus) {
        return orderService.insertOrder(orderid, name, goodsid, price, numbers, orderTime, orderStatus);
    }

    @PostMapping("updateOrder")
    public int updateOrder(String orderid, String orderStatus) {
        return orderService.updateOrder(orderid, orderStatus);
    }

    @PostMapping("payOrder")
    @RequiresAuthentication
    public synchronized MaoqinObject payOrder(String orderid, HttpServletRequest request) {
        MaoqinObject maoqinObject = new MaoqinObject();
        String username = JWTUtil.getCurrentUsername(request);
        try {
            //校验入参
            if (StringTools.isNullOrEmpty(orderid)) {
                maoqinObject.setM(400);
                maoqinObject.setMessage("需要有订单号");
                return maoqinObject;
            }
            Order order = orderService.getOrderByOrderid(orderid);
            User user = userService.getUserByName(username);
            Goods goods = goodsService.getGoodsByGoodsid(order.getGoodsid());
            BigDecimal total = order.getPrice().multiply(new BigDecimal(order.getNumbers()));
            if (order.getOrderStatus().equals("pay")) {
                maoqinObject.setM(400);
                maoqinObject.setMessage("已支付的订单，不用重新支付");
                return maoqinObject;
            }
            if (user.getBalance().compareTo(total) < 0) {
                maoqinObject.setM(400);
                maoqinObject.setMessage("余额不足，请充值");
                return maoqinObject;
            }
            if (goods.getNumbers() < order.getNumbers()) {
                maoqinObject.setM(400);
                maoqinObject.setMessage("目前商品数量不足");
                return maoqinObject;
            }

            BigDecimal balance = user.getBalance().subtract(total);
            Integer blResult = userService.updateBalance(username, balance);
            if (blResult == 0) {
                maoqinObject.setM(400);
                maoqinObject.setMessage("支付失败，请重试");
                return maoqinObject;
            }
            Integer nu = goods.getNumbers() - order.getNumbers();
            Integer nuResult = goodsService.updateGoods(goods.getGoodsid(), nu);
            if (nuResult == 0) {
                userService.updateBalance(username, user.getBalance());
                maoqinObject.setM(400);
                maoqinObject.setMessage("支付失败，请重试");
                return maoqinObject;
            }
            Integer paresult = orderService.updateOrder(orderid, "pay");
            if (paresult == 0) {
                goodsService.updateGoods(goods.getGoodsid(), goods.getNumbers());
                userService.updateBalance(username, user.getBalance());
                maoqinObject.setM(400);
                maoqinObject.setMessage("支付失败，请重试");
                return maoqinObject;
            }
            maoqinObject.setM(1);
            maoqinObject.setMessage("支付成功，谢谢惠顾");
            maoqinObject.setObject(orderService.getOrderGoodsNameByOrderid(orderid));
            return maoqinObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        maoqinObject.setM(400);
        maoqinObject.setMessage("支付失败，请重试");
        return maoqinObject;
    }

    @PostMapping("creatOrder")
    @RequiresAuthentication
    public MaoqinObject creatOrder(String goodsid, Integer numbers, HttpServletRequest request) {
        MaoqinObject maoqinObject = new MaoqinObject();   //新建自定义的对象来接受返回值
        String username = JWTUtil.getCurrentUsername(request);  //通过请求解密用户名
        try {
            if (StringTools.isNullOrEmpty(goodsid)) {        //校验货号入参
                maoqinObject.setM(400);
                maoqinObject.setMessage("商品id不能为空");
                return maoqinObject;
            }
            if (numbers == null || numbers == 0) {              //校验商品数量入参
                maoqinObject.setM(400);
                maoqinObject.setMessage("商品数量不能为空或是0");
                return maoqinObject;
            }
            Goods goods = goodsService.getGoodsByGoodsid(goodsid);    //新建商品查询的对象
            if (goods == null) {                      //校验商品是否存在
                maoqinObject.setM(400);
                maoqinObject.setMessage("商品不存在");
                return maoqinObject;
            }
            if (goods.getNumbers() < numbers) {              //校验商品数量是否够
                maoqinObject.setM(400);
                maoqinObject.setMessage("商品数量不够");
            }
            Price price = priceService.getPriceByGoodsid(goodsid);   //新建商品价格查询的对象
            if (price == null) {                        //校验商品价格是否存在
                maoqinObject.setM(400);
                maoqinObject.setMessage("没有商品价格");
                return maoqinObject;
            }
            String orderid = StringTools.getTradeno();       //生成货号
//        Order order = new Order();
//        order.setOrderid(orderid);
//        order.setGoodsid(goodsid);
//        order.setName(username);
//        order.setOrderStatus("no pay");
//        order.setNumbers(numbers);
//        order.setPrice(price.getPrice());
//        order.setOrderTime(new Date());
            BigDecimal price1 = price.getPrice();
            Date orderTime = new Date();           //生成下单时间
            String orderStatus = "no pay";          //生成下单后的状态
            int result = orderService.insertOrder     //下单插入
                    (orderid, username, goodsid, price1, numbers, orderTime, orderStatus);
            if (result == 0) {                     //校验下单是否成功
                maoqinObject.setM(400);
                maoqinObject.setMessage("生成订单失败");
                return maoqinObject;
            }
            maoqinObject.setM(1);
            maoqinObject.setMessage("下单成功，尽快支付");
            maoqinObject.setObject(orderService.getOrderGoodsNameByOrderid(orderid));
            return maoqinObject;              //返回下单成功信息
        } catch (Exception e) {
            e.printStackTrace();
        }
        maoqinObject.setM(400);
        maoqinObject.setMessage("下单异常");
        return maoqinObject;        //下单有try catch来捕获异常，所以这里就是下单不成功，需要返回信息
    }

    @PostMapping("createOrder")
    @RequiresAuthentication
    public MaoqinObject createOrder(HttpServletRequest request, String goodsid, Integer numbers) {
        MaoqinObject maoqinObject = new MaoqinObject();
        //使用try catch 如果有异常则返回有异常
        try {
            String username = JWTUtil.getCurrentUsername(request);
            //先进行入参校验,如果是空则直接打回
            if (StringTools.isNullOrEmpty(goodsid)) {
                maoqinObject.setM(500);
                maoqinObject.setMessage("商品id不能为空");
                return maoqinObject;
            }
            if (numbers == null || numbers == 0) {
                maoqinObject.setM(500);
                maoqinObject.setMessage("商品数量不能为空");
                return maoqinObject;
            }
            //校验商品id是否在数据库中能找到商品,如果不能则直接打回
            Goods goods = goodsService.selectById(goodsid);
            if (null == goods) {
                maoqinObject.setM(500);
                maoqinObject.setMessage("找不到对应的商品");
                return maoqinObject;
            }
            //查询商品的价格,如果找不到价格直接打回
            Price price = priceService.selectOne(new EntityWrapper<Price>().eq("goodsid", goodsid));
            if (null == price) {
                maoqinObject.setM(500);
                maoqinObject.setMessage("找不到商品对应的价格");
                return maoqinObject;
            }
            //初始化一个订单对象
            Order order = new Order();
            //使用工具类生成订单号
            String orderId = StringTools.getTradeno();
            //计算总价,总价=商品单价*数量
            BigDecimal totalPrice = new BigDecimal(numbers).multiply(price.getPrice());
            //给订单对象设值
            order.setOrderid(orderId);
            order.setGoodsid(goodsid);
            order.setName(username);
            order.setPrice(totalPrice);
            order.setOrderTime(new Date());
            order.setOrderStatus("待支付");
            order.setNumbers(numbers);
            //将订单对象插入数据库,判断插入结果
            boolean result = orderService.insert(order);
            if (!result) {
                maoqinObject.setM(500);
                maoqinObject.setMessage("生成订单失败,请重试");
                return maoqinObject;
            }
            maoqinObject.setM(200);
            maoqinObject.setMessage("下单成功");
            maoqinObject.setObject(orderService.getOrderGoodsNameByOrderid(orderId));
            return maoqinObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        maoqinObject.setM(500);
        maoqinObject.setMessage("下单异常");
        return maoqinObject;
    }

}
