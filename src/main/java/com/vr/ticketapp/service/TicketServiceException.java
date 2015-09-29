package com.vr.ticketapp.service;

public class TicketServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TicketServiceException() { super(); }

    public TicketServiceException(String errMsg) { super(errMsg); }

    public TicketServiceException(String errMsg, Throwable cause) { super(errMsg,cause); }

    public TicketServiceException(Throwable cause) { super(cause); }
}
