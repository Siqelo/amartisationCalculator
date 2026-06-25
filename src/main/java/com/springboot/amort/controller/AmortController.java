package com.springboot.amort.controller;

import com.springboot.amort.context.InvokerContext;
import com.springboot.amort.dto.AmortRequest;
import com.springboot.amort.dto.AmortResponse;
import com.springboot.amort.service.AmortService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AmortController {

    private final AmortService amortService;

    public AmortController(AmortService amortService) {
        this.amortService = amortService;
    }

    @PostMapping("/amortisation")
    public AmortResponse calculate(
            @RequestBody AmortRequest request,
            @RequestHeader(value = "X-Invoker-Id", defaultValue = "wird-amort-web") String invokerId,
            @RequestHeader(value = "X-Invoker-Username") String invokerUsername,
            HttpServletRequest httpRequest) {

        String requestId = (String) httpRequest.getAttribute("X-Request-Id");
        String ipAddress = resolveClientIp(httpRequest);

        InvokerContext invokerContext = new InvokerContext(
                invokerId,
                invokerUsername,
                requestId,
                ipAddress
        );

        return amortService.calculate(request, invokerContext);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
