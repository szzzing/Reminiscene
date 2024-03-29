package com.szzzing.api.service;

import com.szzzing.api.dto.mail.MailDto;
import com.szzzing.api.dto.mail.CodeDto;

public interface MailService {

    // 난수 코드 생성
    public int createCode();

    // 이메일 전송
    public void sendMail(MailDto mailDto);

    public boolean sendAuthCode(MailDto mailDto);

    public boolean sendFindId(MailDto mailDto);

    public boolean sendFindPw(MailDto mailDto);

    public boolean matchCode(CodeDto codeDto);
}
