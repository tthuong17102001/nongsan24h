package com.da.nongsan24.service.impl;

import com.da.nongsan24.dto.MailInfo;
import com.da.nongsan24.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendMailServiceImplement implements SendMailService {
    @Autowired
    JavaMailSender sender;
    List<MailInfo> list = new ArrayList<>();
    @Override
    @Scheduled(fixedDelay = 5000)
    public void run() {
        while (!list.isEmpty()){
            MailInfo mailInfo = list.remove(0);
            try{
                this.send(mailInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfo(to,subject,body));
    }

    @Override
    public void queue(MailInfo mail) {
        list.add(mail);
    }

    @Override
    public void send(MailInfo mail) throws MessagingException, IOException {
        MimeMessage message = sender.createMimeMessage(); //doi tuong sender de gui thu dien tu
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        helper.setFrom(mail.getFrom()); // thiet lap nguoi gui
        helper.setTo(mail.getTo()); // nguoi nhan
        helper.setSubject(mail.getSubject()); // tieu de
        helper.setText(mail.getBody(), true); // noi dung
        helper.setReplyTo(mail.getFrom()); //dia chi email tra loi
        if(mail.getAttachments() != null){
            FileSystemResource file = new FileSystemResource(new File(mail.getAttachments()));
            helper.addAttachment(mail.getAttachments(), file);
        }
        sender.send(message);
    }
}
