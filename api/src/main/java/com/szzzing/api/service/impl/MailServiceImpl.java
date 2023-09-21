package com.szzzing.api.service.impl;

import com.szzzing.api.dto.common.MailDto;
import com.szzzing.api.dto.mail.CodeDto;
import com.szzzing.api.dto.mail.MailRedisDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.repository.MailRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    // 난수 코드 생성
    public int createCode() {
        int code = (int)(Math.random() * (90000)) + 100000;
        log.info(code+"");
        return code;
    }
    // 이메일 전송
    public void sendMail(MailDto mailDto) {
        log.info(mailDto.toString());
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setRecipients(MimeMessage.RecipientType.TO, mailDto.getTo());
            message.setSubject(mailDto.getSubject());
            message.setText(mailDto.getBody(),"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public boolean sendAuthCode(MailDto mailDto) {
        int code = createCode();
        String body = "<h3>️🧙 이메일 인증번호</h3>";
        body += "<b>"+code+"</b>";
        
        mailDto.setBody(body);
        mailDto.setSubject("Reminiscene 이메일 인증번호를 보내드립니다.");
        sendMail(mailDto);
        
        // redis 저장
        MailRedisDto mailRedisDto = new MailRedisDto();
        mailRedisDto.setEmail(mailDto.getTo());
        mailRedisDto.setType("E");
        mailRedisDto.setCode(code);

        mailRepository.save(mailRedisDto);

        return true;
    }

    public boolean sendFindId(MailDto mailDto) {
        UserDto userDto = userRepository.selectOneByEmail(mailDto.getTo());
        if(userDto==null) return false;
        String id = userDto.getId();
        id = id.substring(0, 4);
        id += "****";

        String body = "<h3>️🧙 아이디 검색 결과</h3>";
        body += "이 이메일로 가입하신 아이디는<br>";
        body += "<b>"+id+"</b>입니다.";

        mailDto.setBody(body);
        mailDto.setSubject("Reminiscene 아이디 찾기 결과 보내드립니다.");
        sendMail(mailDto);

        return true;
    }

    public int sendFindPw(MailDto mailDto) {
        int code = createCode();

        String body = "<h3>️🧙 비밀번호 재설정 인증번호</h3>";
        body += "<b>"+code+"</b>";

        mailDto.setBody(body);
        mailDto.setSubject("Reminiscene 비밀번호 찾기 인증번호를 보내드립니다.");
        sendMail(mailDto);

        // redis 저장
        MailRedisDto mailRedisDto = new MailRedisDto();
        mailRedisDto.setEmail(mailDto.getTo());
        mailRedisDto.setType("P");
        mailRedisDto.setCode(code);

        mailRepository.save(mailRedisDto);

        return code;
    }

    @Override
    public boolean matchCode(CodeDto codeDto) {
        return mailRepository.match(codeDto);
    }
}
