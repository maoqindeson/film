package com.leoleo.film.service;


import com.baomidou.mybatisplus.service.IService;
import com.leoleo.film.entity.GroupBuy;

import java.util.Date;

public interface GroupBuyService extends IService< GroupBuy> {
    GroupBuy getGroupBuyById(Integer id);
    Integer countGroupBuyNumbers(Date createdDate,Date endDate,Integer groupBuyId);
    int insertGroupBuy(GroupBuy groupBuy);
    int updateGroupBuyStatus(Integer id,Integer status);
    int updateGroupBuyUpdatedDate(Integer id,Date updatedDate);

}