package com.springboot.amort.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_history")
public class CalculationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id", nullable = false, unique = true, length = 80)
    private String requestId;

    @Column(name = "invoker_id", nullable = false, length = 100)
    private String invokerId;

    @Column(name = "invoker_username", nullable = false, length = 255)
    private String invokerUsername;

  //  @Column(name = "ip_address", length = 80)
    //private String ipAddress;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principal;

    @Column(name = "annual_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal annualRate;

    @Column(name = "period_months", nullable = false)
    private int periodMonths;

    @Column(name = "inception_date", nullable = false)
    private LocalDate inceptionDate;

    @Column(name = "monthly_payment", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyPayment;

    @Column(name = "total_repayment", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalRepayment;

    @Column(name = "total_interest", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalInterest;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public String getInvokerId() { return invokerId; }
    public void setInvokerId(String invokerId) { this.invokerId = invokerId; }
    public String getInvokerUsername() { return invokerUsername; }
    public void setInvokerUsername(String invokerUsername) { this.invokerUsername = invokerUsername; }
  //  public String getIpAddress() { return ipAddress; }
   // public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public BigDecimal getPrincipal() { return principal; }
    public void setPrincipal(BigDecimal principal) { this.principal = principal; }
    public BigDecimal getAnnualRate() { return annualRate; }
    public void setAnnualRate(BigDecimal annualRate) { this.annualRate = annualRate; }
    public int getPeriodMonths() { return periodMonths; }
    public void setPeriodMonths(int periodMonths) { this.periodMonths = periodMonths; }
    public LocalDate getInceptionDate() { return inceptionDate; }
    public void setInceptionDate(LocalDate inceptionDate) { this.inceptionDate = inceptionDate; }
    public BigDecimal getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(BigDecimal monthlyPayment) { this.monthlyPayment = monthlyPayment; }
    public BigDecimal getTotalRepayment() { return totalRepayment; }
    public void setTotalRepayment(BigDecimal totalRepayment) { this.totalRepayment = totalRepayment; }
    public BigDecimal getTotalInterest() { return totalInterest; }
    public void setTotalInterest(BigDecimal totalInterest) { this.totalInterest = totalInterest; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
