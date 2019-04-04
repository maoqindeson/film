package com.leoleo.film.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.leoleo.film.dao.GroupBuyDao;
import com.leoleo.film.entity.GroupBuy;
import com.leoleo.film.service.GroupBuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service("groupBuyService")
public class GroupBuyServiceImpl extends ServiceImpl<GroupBuyDao, GroupBuy>implements GroupBuyService {

    @Override
    public GroupBuy getGroupBuyById(Integer id) {
        return baseMapper.getGroupBuyById(id);
    }

    @Override
    public Integer countGroupBuyNumbers(Date createdDate, Date endDate, Integer groupBuyId) {
        return baseMapper.countGroupBuyNumbers(createdDate, endDate, groupBuyId);
    }

    @Override
    public int insertGroupBuy(GroupBuy groupBuy) {
        return baseMapper.insertGroupBuy(groupBuy);
    }

    @Override
    public int updateGroupBuyStatus(Integer id, Integer status) {
        return baseMapper.updateGroupBuyStatus(id, status);
    }

    @Override
    public int updateGroupBuyUpdatedDate(Integer id, Date updatedDate) {
        return baseMapper.updateGroupBuyUpdatedDate(id, updatedDate);
    }
}
