package com.health.reservation.service.impl;

import java.util.List;
import com.health.common.utils.DateUtils;
import com.health.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.health.reservation.mapper.TSetmealMapper;
import com.health.reservation.mapper.TSetmealCheckgroupMapper;
import com.health.reservation.domain.TSetmeal;
import com.health.reservation.dto.TSetmealDto;
import com.health.reservation.vo.TSetmealVo;
import com.health.reservation.service.ITSetmealService;

@Service
public class TSetmealServiceImpl implements ITSetmealService 
{
    @Autowired
    private TSetmealMapper tSetmealMapper;

    @Autowired
    private TSetmealCheckgroupMapper tSetmealCheckgroupMapper;

    @Override
    public TSetmealVo selectTSetmealById(Long id)
    {
        TSetmeal tSetmeal = tSetmealMapper.selectTSetmealById(id);
        List<Long> list = tSetmealCheckgroupMapper.selectCheckgroupIds(id);
        TSetmealVo vo = new TSetmealVo();
        BeanUtils.copyProperties(tSetmeal, vo);
        vo.setCheckgroupIds(list);
        return vo;
    }

    @Override
    public List<TSetmeal> selectTSetmealList(TSetmeal tSetmeal)
    {
        return tSetmealMapper.selectTSetmealList(tSetmeal);
    }

    @Transactional
    @Override
    public int insertTSetmeal(TSetmealDto dto)
    {
        dto.setCreateTime(DateUtils.getNowDate());
        int count = tSetmealMapper.insertTSetmeal(dto);
        if (dto.getCheckgroupIds() != null && dto.getCheckgroupIds().size() > 0)
        {
            tSetmealCheckgroupMapper.insertCheckgroupIds(dto.getCheckgroupIds(), dto.getId());
        }
        return count;
    }

    @Transactional
    @Override
    public int updateTSetmeal(TSetmealDto dto)
    {
        if (dto.getCheckgroupIds() != null && dto.getCheckgroupIds().size() > 0)
        {
            tSetmealCheckgroupMapper.deleteTSetmealCheckgroupBySetmealId(dto.getId());
            tSetmealCheckgroupMapper.insertCheckgroupIds(dto.getCheckgroupIds(), dto.getId());
        }
        dto.setUpdateTime(DateUtils.getNowDate());
        return tSetmealMapper.updateTSetmeal(dto);
    }

    @Transactional
    @Override
    public int deleteTSetmealByIds(Long[] ids)
    {
        for (Long id : ids)
        {
            tSetmealCheckgroupMapper.deleteTSetmealCheckgroupBySetmealId(id);
        }
        return tSetmealMapper.deleteTSetmealByIds(ids);
    }

    @Transactional
    @Override
    public int deleteTSetmealById(Long id)
    {
        tSetmealCheckgroupMapper.deleteTSetmealCheckgroupBySetmealId(id);
        return tSetmealMapper.deleteTSetmealById(id);
    }
}
