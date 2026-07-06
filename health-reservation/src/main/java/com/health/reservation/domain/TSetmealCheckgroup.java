package com.health.reservation.domain;

import com.health.common.core.domain.BaseEntity;

public class TSetmealCheckgroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long setmealId;

    private Long checkgroupId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setSetmealId(Long setmealId)
    {
        this.setmealId = setmealId;
    }

    public Long getSetmealId()
    {
        return setmealId;
    }

    public void setCheckgroupId(Long checkgroupId)
    {
        this.checkgroupId = checkgroupId;
    }

    public Long getCheckgroupId()
    {
        return checkgroupId;
    }
}
