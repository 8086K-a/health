package com.health.reservation.service;

import java.util.List;
import com.health.reservation.domain.TSetmeal;
import com.health.reservation.dto.TSetmealDto;
import com.health.reservation.vo.TSetmealVo;

public interface ITSetmealService 
{
    public TSetmealVo selectTSetmealById(Long id);

    public List<TSetmeal> selectTSetmealList(TSetmeal tSetmeal);

    public int insertTSetmeal(TSetmealDto dto);

    public int updateTSetmeal(TSetmealDto dto);

    public int deleteTSetmealByIds(Long[] ids);

    public int deleteTSetmealById(Long id);
}
