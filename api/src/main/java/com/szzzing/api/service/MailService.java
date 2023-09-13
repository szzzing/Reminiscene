package com.szzzing.api.service;

import com.szzzing.api.dto.common.EmailDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

public interface MailService {

    // 난수 코드 생성
    public int createCode();

    // 이메일 전송
    public void sendMail(EmailDto emailDto);

    public int sendAuthCode(EmailDto emailDto);

    public boolean sendFindId(EmailDto emailDto);

    public int sendFindPw(EmailDto emailDto);
}
