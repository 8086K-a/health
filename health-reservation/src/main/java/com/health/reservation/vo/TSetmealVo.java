package com.health.reservation.vo;

import com.health.reservation.domain.TSetmeal;

import java.util.List;

public class TSetmealVo extends TSetmeal {
    private List<Long> checkgroupIds;

    public List<Long> getCheckgroupIds() {
        return checkgroupIds;
    }

    public void setCheckgroupIds(List<Long> checkgroupIds) {
        this.checkgroupIds = checkgroupIds;
    }

    @Override
    public String toString() {
        return "TSetmealVo{" +
                "checkgroupIds=" + checkgroupIds +
                '}';
    }
}
