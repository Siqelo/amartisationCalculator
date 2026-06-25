package com.springboot.amort.service;

import com.springboot.amort.context.InvokerContext;
import com.springboot.amort.dto.AmortRequest;
import com.springboot.amort.dto.AmortResponse;
import com.springboot.amort.dto.ScheduleRow;
import com.springboot.amort.entity.CalculationHistory;
import com.springboot.amort.repository.CalculationHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmortService {

    private final CalculationHistoryRepository calculationHistoryRepository;

    public AmortService(CalculationHistoryRepository calculationHistoryRepository) {
        this.calculationHistoryRepository = calculationHistoryRepository;
    }

    public AmortResponse calculate(AmortRequest request, InvokerContext invokerContext) {
        validate(request, invokerContext);

        double principal = request.getPrincipal();
        double monthlyRate = request.getAnnualRate() / 100.0 / 12.0;
        int periodMonths = request.getPeriodMonths();
        LocalDate inceptionDate = LocalDate.parse(request.getInceptionDate());

        double monthlyPayment;
        if (monthlyRate == 0) {
            monthlyPayment = principal / periodMonths;
        } else {
            monthlyPayment = principal * monthlyRate * Math.pow(1 + monthlyRate, periodMonths)
                    / (Math.pow(1 + monthlyRate, periodMonths) - 1);
        }

        List<ScheduleRow> schedule = new ArrayList<>();
        double balance = principal;
        double totalInterest = 0.0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (int month = 1; month <= periodMonths; month++) {
            double interest = balance * monthlyRate;
            double principalPortion = monthlyPayment - interest;

            if (month == periodMonths || principalPortion > balance) {
                principalPortion = balance;
                monthlyPayment = principalPortion + interest;
            }

            balance = Math.max(0.0, balance - principalPortion);
            totalInterest += interest;

            schedule.add(new ScheduleRow(
                    inceptionDate.plusMonths(month).format(formatter),
                    monthlyPayment,
                    principalPortion,
                    interest,
                    balance
            ));
        }

        double totalRepayment = principal + totalInterest;

        saveHistory(request, invokerContext, monthlyPayment, totalRepayment, totalInterest, inceptionDate);

        return new AmortResponse(
                monthlyPayment,
                totalRepayment,
                totalInterest,
                invokerContext.getRequestId(),
                invokerContext.getInvokerId(),
                invokerContext.getInvokerUsername(),
                schedule
        );
    }

    private void saveHistory(AmortRequest request,
                             InvokerContext invokerContext,
                             double monthlyPayment,
                             double totalRepayment,
                             double totalInterest,
                             LocalDate inceptionDate) {
        CalculationHistory history = new CalculationHistory();
        history.setRequestId(invokerContext.getRequestId());
        history.setInvokerId(invokerContext.getInvokerId());
        history.setInvokerUsername(invokerContext.getInvokerUsername());
      //  history.setIpAddress(invokerContext.getIpAddress());
        history.setPrincipal(toMoney(request.getPrincipal()));
        history.setAnnualRate(toMoney(request.getAnnualRate()));
        history.setPeriodMonths(request.getPeriodMonths());
        history.setInceptionDate(inceptionDate);
        history.setMonthlyPayment(toMoney(monthlyPayment));
        history.setTotalRepayment(toMoney(totalRepayment));
        history.setTotalInterest(toMoney(totalInterest));

        calculationHistoryRepository.save(history);
    }

    private BigDecimal toMoney(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private void validate(AmortRequest request, InvokerContext invokerContext) {
        if (request.getPrincipal() <= 0) throw new IllegalArgumentException("Principal must be greater than zero.");
        if (request.getAnnualRate() < 0) throw new IllegalArgumentException("Annual rate cannot be negative.");
        if (request.getPeriodMonths() <= 0) throw new IllegalArgumentException("Period months must be greater than zero.");
        if (request.getInceptionDate() == null || request.getInceptionDate().isBlank()) throw new IllegalArgumentException("Inception date is required.");
        if (invokerContext.getInvokerUsername() == null || invokerContext.getInvokerUsername().isBlank()) throw new IllegalArgumentException("Invoker username is required.");
    }
}
