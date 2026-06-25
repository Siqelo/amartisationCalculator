package com.springboot.amort.context;

public class InvokerContext {
    private final String invokerId;
    private final String invokerUsername;
    private final String requestId;
    private final String ipAddress;

    public InvokerContext(String invokerId, String invokerUsername, String requestId, String ipAddress) {
        this.invokerId = invokerId;
        this.invokerUsername = invokerUsername;
        this.requestId = requestId;
        this.ipAddress = ipAddress;
    }

    public String getInvokerId() { return invokerId; }
    public String getInvokerUsername() { return invokerUsername; }
    public String getRequestId() { return requestId; }
    public String getIpAddress() { return ipAddress; }
}
