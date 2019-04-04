package com.leoleo.film.utils;

import com.leoleo.film.entity.GroupBuy;
import com.leoleo.film.service.GroupBuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@Configuration
@EnableScheduling
public class FilmTask {
    @Autowired
    private GroupBuyService groupBuyService;

    @Scheduled(cron = "0 */1  * * * ? ")  //每1分执行一次
    public void task() {
        GroupBuy groupBuy = groupBuyService.getGroupBuyById(1);
        Date creatDate = groupBuy.getCreatedDate();
        Date endDate = groupBuy.getEndDate();
        if (groupBuy.getCreatedDate().compareTo(new Date()) == 0) {
            groupBuyService.updateGroupBuyStatus(1, 1);
            groupBuyService.updateGroupBuyUpdatedDate(1, new Date());
        }
        if (groupBuy.getCreatedDate().compareTo(new Date()) <= 0 && groupBuy.getCreatedDate().compareTo(new Date()) >= 0) {
            if (groupBuyService.countGroupBuyNumbers(creatDate, endDate, 1) >= groupBuy.getMiniNum()) {
                groupBuyService.updateGroupBuyStatus(1, 2);
                groupBuyService.updateGroupBuyUpdatedDate(1, new Date());
            }
        }
        if (groupBuy.getEndDate().compareTo(new Date()) == 0) {
            groupBuyService.updateGroupBuyStatus(1, 3);
            groupBuyService.updateGroupBuyUpdatedDate(1, new Date());
        }
    }
}
