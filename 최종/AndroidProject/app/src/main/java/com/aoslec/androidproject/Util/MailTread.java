package com.aoslec.androidproject.Util;

import android.content.Context;
import android.widget.Toast;

import com.aoslec.androidproject.Share.ShareVar;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

/**
 * Created by biso on 2021/06/22.
 */
public class MailTread extends Thread{

    private Context context;
    private String pinCode;

    private String checkedEmail;
    public MailTread() {
    }

    public MailTread(String checkedEmail, Context context) {
        this.checkedEmail = checkedEmail;
        this.context = context;
    }

    public void run(){
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //인증코드
        pinCode = gMailSender.getEmailCode();
        try {
            gMailSender.sendMail("Weather Email code", pinCode , checkedEmail);
        } catch (SendFailedException e) {
            ShareVar.login_fail = 1;
            Toast.makeText(context, "이메일 형식을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            ShareVar.login_fail = 1;
            System.out.println("인터넷 문제"+e);
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ShareVar.login_fail = 1;
            e.printStackTrace();
        }
        Toast.makeText(context, "인증 메일 전송", Toast.LENGTH_SHORT).show();
        ShareVar.login_fail = 0;

        pinCodeReturn();
    }

    public String pinCodeReturn() {
        return pinCode;
    }
}
