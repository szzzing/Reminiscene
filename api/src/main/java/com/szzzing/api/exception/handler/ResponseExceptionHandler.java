package com.szzzing.api.exception.handler;

import com.szzzing.api.exception.FileException;
import com.szzzing.api.exception.MailSendException;
import com.szzzing.api.exception.message.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {
    private static final String HEADER_STRING = "message";

    @ExceptionHandler(MailSendException.class)
    protected ResponseEntity<?> handleMailSendException(MailSendException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_STRING, StatusMessage.MAIL_SEND_FAILED.msg());

        log.info("예외 발생 : "+this.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
    }

    @ExceptionHandler(FileException.class)
    protected ResponseEntity<?> handleFileException(FileException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_STRING, StatusMessage.FILE_UPLOAD_FAILED.msg());

        log.info("예외 발생 : "+this.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
    }
}
