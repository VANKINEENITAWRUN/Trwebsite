package com.tawrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class PayInfo {
    private String to;
    private float amount;
    private Date date;

    public PayInfo(float amount, String to, Date date){
        this.to = to;
        this.amount = amount;
        this.date = date;
    }
}
