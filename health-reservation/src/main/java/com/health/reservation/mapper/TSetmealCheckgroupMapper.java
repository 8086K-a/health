package com.health.reservation.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TSetmealCheckgroupMapper
{
    int insertCheckgroupIds(@Param("list") List<Long> checkgroupIds, @Param("setmealId") Long setmealId);

    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id=#{id}")
    List<Long> selectCheckgroupIds(Long id);

    int deleteTSetmealCheckgroupBySetmealId(Long setmealId);

    @Select("select count(1) from t_setmeal_checkgroup where checkgroup_id=#{id}")
    int selectCountByCheckgroupId(Long id);
}
