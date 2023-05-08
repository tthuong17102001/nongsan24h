package com.da.nongsan24.service;

import com.da.nongsan24.dto.MailInfo;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
@Service
public interface SendMailService {
    void run();

    void queue(String to, String subject, String body);

    void queue(MailInfo mail);

    void send(MailInfo mail) throws MessagingException, IOException;
}
