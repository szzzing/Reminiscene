package com.szzzing.api.service.impl;

import com.szzzing.api.dto.common.EmailDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    // 난수 코드 생성
    public int createCode() {
        int code = (int)(Math.random() * (90000)) + 100000;
        log.info(code+"");
        return code;
    }
    // 이메일 전송
    public void sendMail(EmailDto emailDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setRecipients(MimeMessage.RecipientType.TO, emailDto.getTo());
            message.setSubject(emailDto.getSubject());
            message.setText(emailDto.getBody(),"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public int sendAuthCode(EmailDto emailDto) {
        int code = createCode();
        String body = "<h3>️🧙 회원가입 인증번호</h3>";
        body += "<b>"+code+"</b>";
        
        emailDto.setBody(body);
        emailDto.setSubject("Reminiscene 회원가입 인증번호를 보내드립니다.");
        sendMail(emailDto);

        return code;
    }

    public boolean sendFindId(EmailDto emailDto) {
        UserDto userDto = userRepository.selectOneByEmail(emailDto.getTo());
        if(userDto==null) return false;
        String id = userDto.getId();
        id = id.substring(0, 4);
        id += "****";

        String body = "<h3>️🧙 아이디 검색 결과</h3>";
        body += "이 이메일로 가입하신 아이디는<br>";
        body += "<b>"+id+"</b>입니다.";

        emailDto.setBody(body);
        emailDto.setSubject("Reminiscene 아이디 찾기 결과 보내드립니다.");
        sendMail(emailDto);

        return true;
    }

    public int sendFindPw(EmailDto emailDto) {
        int code = createCode();

        String body = "<h3>️🧙 비밀번호 재설정 인증번호</h3>";
        body += "<b>"+code+"</b>";

        emailDto.setBody(body);
        emailDto.setSubject("Reminiscene 비밀번호 찾기 인증번호를 보내드립니다.");
        sendMail(emailDto);

        return code;
    }
}
