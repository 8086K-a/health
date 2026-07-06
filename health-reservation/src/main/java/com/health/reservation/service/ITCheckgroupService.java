package com.health.reservation.service;

import java.util.List;
import com.health.reservation.domain.TCheckgroup;
import com.health.reservation.dto.TCheckgroupDto;
import com.health.reservation.vo.TCheckgroupVo;

public interface ITCheckgroupService
{
    public TCheckgroupVo selectTCheckgroupById(Long id);

    public List<TCheckgroup> selectTCheckgroupList(TCheckgroup tCheckgroup);

    public int insertTCheckgroup(TCheckgroupDto dto);

    public int updateTCheckgroup(TCheckgroupDto dto);

    public int deleteTCheckgroupById(Long id);

    public int deleteTCheckgroupByIds(Long[] ids);
}
