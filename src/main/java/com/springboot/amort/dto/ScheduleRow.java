package com.springboot.amort.dto;

public class ScheduleRow {
    private String date;
    private double payment;
    private double principal;
    private double interest;
    private double balance;

    public ScheduleRow(String date, double payment, double principal, double interest, double balance) {
        this.date = date;
        this.payment = payment;
        this.principal = principal;
        this.interest = interest;
        this.balance = balance;
    }

    public String getDate() { return date; }
    public double getPayment() { return payment; }
    public double getPrincipal() { return principal; }
    public double getInterest() { return interest; }
    public double getBalance() { return balance; }
}
