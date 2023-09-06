package com.szzzing.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private static final String SENDER = "r87329584@gmail.com";

    public static int createCode() {
        int code = (int)(Math.random() * (90000)) + 100000;
        log.info(code+"");
        return code;
    }

    public MimeMessage createMail(String email, int code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(SENDER);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("Reminiscene의 이메일 인증코드를 보내드립니다.");
            String body = "<h1>️🧙 " + code + "</h1>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String email) {
        int code = createCode();
        MimeMessage message = createMail(email, code);
        javaMailSender.send(message);

        return code;
    }
}
