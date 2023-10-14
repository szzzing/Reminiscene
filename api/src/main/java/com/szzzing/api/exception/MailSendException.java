package com.szzzing.api.exception;

public class MailSendException extends RuntimeException {
    public MailSendException() {
        super();
    }
    public MailSendException(String msg) {
        super(msg);
    }
}
