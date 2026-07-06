package com.health.reservation.domain;

import com.health.common.core.domain.BaseEntity;

public class TCheckgroupCheckitem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long checkgroupId;

    private Long checkitemId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setCheckgroupId(Long checkgroupId)
    {
        this.checkgroupId = checkgroupId;
    }

    public Long getCheckgroupId()
    {
        return checkgroupId;
    }

    public void setCheckitemId(Long checkitemId)
    {
        this.checkitemId = checkitemId;
    }

    public Long getCheckitemId()
    {
        return checkitemId;
    }
}
