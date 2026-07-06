package com.health.reservation.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TCheckgroupCheckitemMapper
{
    int insertCheckitemIds(@Param("list") List<Long> checkItemIds, @Param("groupId") Long groupId);

    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id=#{id}")
    List<Long> selectCheckitemIds(Long id);

    int deleteTCheckgroupCheckitemByCheckgroupId(Long checkgroupId);
}
