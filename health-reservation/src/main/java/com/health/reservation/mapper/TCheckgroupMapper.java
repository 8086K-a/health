package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TCheckgroup;

public interface TCheckgroupMapper
{
    public TCheckgroup selectTCheckgroupById(Long id);

    public List<TCheckgroup> selectTCheckgroupList(TCheckgroup tCheckgroup);

    public int insertTCheckgroup(TCheckgroup tCheckgroup);

    public int updateTCheckgroup(TCheckgroup tCheckgroup);

    public int deleteTCheckgroupById(Long id);

    public int deleteTCheckgroupByIds(Long[] ids);
}
