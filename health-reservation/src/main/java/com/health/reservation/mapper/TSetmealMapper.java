package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TSetmeal;

public interface TSetmealMapper
{
    public TSetmeal selectTSetmealById(Long id);

    public List<TSetmeal> selectTSetmealList(TSetmeal tSetmeal);

    public int insertTSetmeal(TSetmeal tSetmeal);

    public int updateTSetmeal(TSetmeal tSetmeal);

    public int deleteTSetmealById(Long id);

    public int deleteTSetmealByIds(Long[] ids);
}
