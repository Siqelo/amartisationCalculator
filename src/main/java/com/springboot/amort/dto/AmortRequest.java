package com.springboot.amort.dto;

public class AmortRequest {
    private double principal;
    private double annualRate;
    private int periodMonths;
    private String inceptionDate;

    public double getPrincipal() { return principal; }
    public void setPrincipal(double principal) { this.principal = principal; }
    public double getAnnualRate() { return annualRate; }
    public void setAnnualRate(double annualRate) { this.annualRate = annualRate; }
    public int getPeriodMonths() { return periodMonths; }
    public void setPeriodMonths(int periodMonths) { this.periodMonths = periodMonths; }
    public String getInceptionDate() { return inceptionDate; }
    public void setInceptionDate(String inceptionDate) { this.inceptionDate = inceptionDate; }
}
