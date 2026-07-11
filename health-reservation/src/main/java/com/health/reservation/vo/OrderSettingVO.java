package com.health.reservation.vo;

public class OrderSettingVO {
    private Long id;
    private Integer date;
    private Long number;
    private Long reservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getReservations() {
        return reservations;
    }

    public void setReservations(Long reservations) {
        this.reservations = reservations;
    }
}
