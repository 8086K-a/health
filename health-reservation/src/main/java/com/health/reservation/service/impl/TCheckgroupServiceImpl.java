package com.health.reservation.service.impl;

import java.util.List;
import com.health.common.utils.DateUtils;
import com.health.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.health.reservation.mapper.TCheckgroupMapper;
import com.health.reservation.mapper.TCheckgroupCheckitemMapper;
import com.health.reservation.mapper.TSetmealCheckgroupMapper;
import com.health.reservation.domain.TCheckgroup;
import com.health.reservation.dto.TCheckgroupDto;
import com.health.reservation.vo.TCheckgroupVo;
import com.health.reservation.service.ITCheckgroupService;

@Service
public class TCheckgroupServiceImpl implements ITCheckgroupService
{
    @Autowired
    private TCheckgroupMapper tCheckgroupMapper;

    @Autowired
    private TCheckgroupCheckitemMapper tCheckgroupCheckitemMapper;

    @Autowired
    private TSetmealCheckgroupMapper tSetmealCheckgroupMapper;

    @Override
    public TCheckgroupVo selectTCheckgroupById(Long id)
    {
        TCheckgroup tCheckgroup = tCheckgroupMapper.selectTCheckgroupById(id);
        List<Long> list = tCheckgroupCheckitemMapper.selectCheckitemIds(id);
        TCheckgroupVo vo = new TCheckgroupVo();
        BeanUtils.copyProperties(tCheckgroup, vo);
        vo.setCheckItemIds(list);
        return vo;
    }

    @Override
    public List<TCheckgroup> selectTCheckgroupList(TCheckgroup tCheckgroup)
    {
        return tCheckgroupMapper.selectTCheckgroupList(tCheckgroup);
    }

    @Transactional
    @Override
    public int insertTCheckgroup(TCheckgroupDto dto)
    {
        dto.setCreateTime(DateUtils.getNowDate());
        int count = tCheckgroupMapper.insertTCheckgroup(dto);
        if (dto.getCheckItemIds() != null && dto.getCheckItemIds().size() > 0)
        {
            tCheckgroupCheckitemMapper.insertCheckitemIds(dto.getCheckItemIds(), dto.getId());
        }
        return count;
    }

    @Transactional
    @Override
    public int updateTCheckgroup(TCheckgroupDto dto)
    {
        try
        {
            if (dto.getCheckItemIds() != null && dto.getCheckItemIds().size() > 0)
            {
                tCheckgroupCheckitemMapper.deleteTCheckgroupCheckitemByCheckgroupId(dto.getId());
                tCheckgroupCheckitemMapper.insertCheckitemIds(dto.getCheckItemIds(), dto.getId());
            }
            dto.setUpdateTime(DateUtils.getNowDate());
            return tCheckgroupMapper.updateTCheckgroup(dto);
        }
        catch (Exception e)
        {
            throw new RuntimeException("修改检查组信息失败");
        }
    }

    @Override
    public int deleteTCheckgroupById(Long id)
    {
        int count = tSetmealCheckgroupMapper.selectCountByCheckgroupId(id);
        if (count > 0) {
            throw new RuntimeException("当前检查组被套餐关联，不能删除");
        }
        tCheckgroupCheckitemMapper.deleteTCheckgroupCheckitemByCheckgroupId(id);
        return tCheckgroupMapper.deleteTCheckgroupById(id);
    }

    @Override
    public int deleteTCheckgroupByIds(Long[] ids)
    {
        return tCheckgroupMapper.deleteTCheckgroupByIds(ids);
    }
}
