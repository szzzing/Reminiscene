package com.szzzing.api.exception.message;

public enum StatusMessage {
    LOGIN_FAILED("LOGIN_FAILED"),
    MAIL_SEND_FAILED("MAIL_SEND_FAILED"),
    FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED");

    private final String msg;

    StatusMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
