package com.szzzing.api.service;

import com.szzzing.api.dto.common.MailDto;
import com.szzzing.api.dto.mail.CodeDto;

public interface MailService {

    // 난수 코드 생성
    public int createCode();

    // 이메일 전송
    public void sendMail(MailDto mailDto);

    public boolean sendAuthCode(MailDto mailDto);

    public boolean sendFindId(MailDto mailDto);

    public int sendFindPw(MailDto mailDto);

    boolean matchCode(CodeDto codeDto);
}
