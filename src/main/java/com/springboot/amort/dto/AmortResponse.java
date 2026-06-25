package com.springboot.amort.dto;

import java.util.List;

public class AmortResponse {
    private double monthlyPayment;
    private double totalRepayment;
    private double totalInterest;
    private String requestId;
    private String invokerId;
    private String invokerUsername;
    private List<ScheduleRow> schedule;

    public AmortResponse(double monthlyPayment, double totalRepayment, double totalInterest,
                         String requestId, String invokerId, String invokerUsername,
                         List<ScheduleRow> schedule) {
        this.monthlyPayment = monthlyPayment;
        this.totalRepayment = totalRepayment;
        this.totalInterest = totalInterest;
        this.requestId = requestId;
        this.invokerId = invokerId;
        this.invokerUsername = invokerUsername;
        this.schedule = schedule;
    }

    public double getMonthlyPayment() { return monthlyPayment; }
    public double getTotalRepayment() { return totalRepayment; }
    public double getTotalInterest() { return totalInterest; }
    public String getRequestId() { return requestId; }
    public String getInvokerId() { return invokerId; }
    public String getInvokerUsername() { return invokerUsername; }
    public List<ScheduleRow> getSchedule() { return schedule; }
}
