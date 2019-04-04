package com.leoleo.film.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leoleo.film.entity.GroupBuy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface GroupBuyDao  extends BaseMapper<GroupBuy> {
    GroupBuy getGroupBuyById(Integer id);
    Integer countGroupBuyNumbers(@Param("createdDate")Date createdDate,@Param("endDate")Date endDate,@Param("groupBuyId")Integer groupBuyId);
    int insertGroupBuy(GroupBuy groupBuy);
    int updateGroupBuyStatus(@Param("id")Integer id,@Param("status")Integer status);
    int updateGroupBuyUpdatedDate(@Param("id")Integer id,@Param("updatedDate")Date updatedDate);
}