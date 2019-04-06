package com.leoleo.film.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.leoleo.film.entity.GroupBuy;
import com.leoleo.film.service.GroupBuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class FilmTask {
    @Autowired
    private GroupBuyService groupBuyService;

    @Scheduled(cron = "0 */1  * * * ? ")  //每1分执行一次
    public void task() {
        log.warn("开始定时检查团购");
        List<GroupBuy> list = groupBuyService.selectList(new EntityWrapper<GroupBuy>());
        if (null != list && !list.isEmpty()) {
            for (GroupBuy groupBuy : list) {
                //应该轮询所有团购记录,而不是只看id为1的
                Date endDate = groupBuy.getEndDate();
                //只需要判断结束时间是否小于结束时间
                // 如果团购结束时间到了,则更新团购状态,否则不更新
                if (groupBuy.getEndDate().compareTo(new Date()) < 0) {
                    //查询该团购成功购买的人数,如果达到了则团购成功,否则团购失败
                    Integer num = groupBuyService.countGroupBuyNumbers(groupBuy.getCreatedDate(), endDate, groupBuy.getId());  //查询已售出的团购商品数量
                    if (num>groupBuy.getMiniNum()){
                        log.warn("团购成功,id为 : "+groupBuy.getId());
                        groupBuy.setStatus(3);
                    }
                    groupBuy.setUpdatedDate(new Date());
                    boolean result = groupBuyService.updateById(groupBuy);
                    if (!result) {
                        log.error("更新团购状态失败,团购id为: " + groupBuy.getId());
                    }
                }
            }
        }

    }
}
