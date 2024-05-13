package com.equaled.eserve.common.outlookTest;

import com.equaled.eserve.common.ICSUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class OutlookMain {

    public OutlookMain() {
    }

    /*
     * @param args
     */
    public static void main(String[] args) {
        try {
            Map<ICSUtils.ICSKey,String> map=new HashMap<ICSUtils.ICSKey,String>();
            map.put(ICSUtils.ICSKey.DESCRIPTION,"Testing app");
            map.put(ICSUtils.ICSKey.SUMMARY,"Testing");
            map.put(ICSUtils.ICSKey.MEETING,"https://www.eserve.co/meet");
            map.put(ICSUtils.ICSKey.ORGANIZER,"vikashpandeyeng@gmail.com");
            Set<String> set=new HashSet<String>();
            set.add("vikash@eservecloud.in");
            set.add("vikashpandeyeng@gmail.com");
            Instant from=Instant.now().plus(5, ChronoUnit.HOURS);
            Instant to=Instant.now().plus(6, ChronoUnit.HOURS);
            String randomNumber="1FC51209-1217-4F8E-BC28-1D7AE55A7C94";
            File file=ICSUtils.createICSFile(from,to,randomNumber,map,set);
            send(file,"REQUEST");
            File file1=ICSUtils.createCancelICSFile(from,to,randomNumber,map,set);
            send(file1,"CANCEL");
            // File file1=cancelcreateICSFile(from,to,randomNumber,map,set);
            //System.out.println(Instant.ofEpochMilli(1647527064913l));
            //sendemail(file1,"CANCEL","Cancel");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(File file,String text) throws Exception {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get Session
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("vikashpandeyeng@gmail.com","084212560");
                        }
                    });
            // Define message
            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress("noreply@eservecloud.in"));
            mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress("vikash@eservecloud.in"));
            mimeMsg.setSubject("Outlook Meeting Request Using JavaMail");
            mimeMsg.addHeaderLine("method="+text);
            mimeMsg.addHeaderLine("charset=UTF-8");
            mimeMsg.addHeaderLine("component=VEVENT");

            MimeBodyPart messageBodyPart=new MimeBodyPart();
            //messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(new FileInputStream(file), "text/calendar;method="+text+";name=\"invite.ics\"")));

            // Create a Multipart
            Multipart multipart = new MimeMultipart();

            // Add part one
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            mimeMsg.setContent(multipart);

            // send message
            Transport.send(mimeMsg);
            System.err.println("******* Message Sent ********");
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}