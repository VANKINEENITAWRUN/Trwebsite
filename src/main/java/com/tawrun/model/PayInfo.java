package com.tawrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class PayInfo {
    private String to;
    private int amount;
    private Date date;

    public PayInfo(int amount, String to, Date date){
        this.to = to;
        this.amount = amount;
        this.date = date;
    }
}
