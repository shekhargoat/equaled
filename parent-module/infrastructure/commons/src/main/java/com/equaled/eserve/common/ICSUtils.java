package com.equaled.eserve.common;

import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.component.VAlarm;
import biweekly.component.VEvent;
import biweekly.io.text.ICalWriter;
import biweekly.parameter.CalendarUserType;
import biweekly.property.*;
import biweekly.util.Frequency;
import biweekly.util.Recurrence;
import com.equaled.eserve.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Slf4j
public class ICSUtils {

    public static File createICSFile(Instant startDate, Instant endDateTime, String meetingId,
                                         Map<ICSKey,String> req, Set<String> guests) throws Exception{
        if(startDate==null || endDateTime==null) throw new ApplicationException("Start end date is mandatory");
        ICalendar ical = new ICalendar();
        ical.setProductId("/eServecloud //eServecloud Calendar 1.0//EN");
        ical.addProperty(new Method(Method.REQUEST));
        ical.setCalendarScale(CalendarScale.gregorian());
        VEvent event = new VEvent();
        event.setDateStart(Date.from(startDate));
        event.setDateEnd(Date.from(endDateTime));
        if (req.containsKey(ICSKey.ORGANIZER)) {
            Organizer organizer=new Organizer(req.get(ICSKey.ORGANIZER),req.get(ICSKey.ORGANIZER));
            event.setOrganizer(organizer);
        }else{
            throw new ApplicationException("Organizer should not be empty");
        }
        if (StringUtils.isEmpty(meetingId)) {
            event.setUid(CommonUtils.getEighteenDigitRandomString());
        } else {
            event.setUid(meetingId);
        }
        if (CollectionUtils.isNotEmpty(guests)){
            guests.forEach(gus->{
                Attendee attendee=new Attendee(gus,gus);
                attendee.setCalendarUserType(CalendarUserType.INDIVIDUAL);
                attendee.setParameter("ROLE","REQ-PARTICIPANT");
                attendee.setParameter("PARTSTAT","NEEDS-ACTION");
                attendee.setRsvp(true);
                event.addAttendee(attendee);
            });
        }else{
            throw new ApplicationException("Guest list should not be empty");
        }
        event.setCreated(Date.from(Instant.now()));
        event.setLastModified(Date.from(Instant.now()));
        if (req.containsKey(ICSKey.SUMMARY)) {
            event.setSummary(req.get(ICSKey.SUMMARY));
            event.getSummary().setLanguage("en-us");
        }
        if (req.containsKey(ICSKey.MEETING)) {
            event.setLocation(req.get(ICSKey.MEETING));
        }
        if (req.containsKey(ICSKey.DESCRIPTION)) {
            event.setDescription(req.get(ICSKey.DESCRIPTION));
        }
        event.setSequence(0);
        event.setStatus(Status.confirmed());
        event.setTransparency(Transparency.opaque());

        event.addAlarm(VAlarm.display(new Trigger(event.getDateTimeStamp().getValue()),event.getSummary().getValue()));
        if(req.containsKey(ICSKey.FREQUENCY) && req.containsKey(ICSKey.INTERVAL)){
            Frequency fre=Frequency.valueOf(ICSKey.FREQUENCY.name());
            Recurrence recur = new Recurrence.Builder(fre).interval(Integer.parseInt(ICSKey.INTERVAL.name())).build();
            event.setRecurrenceRule(recur);
        }
        ical.addEvent(event);
        File file = new File("invite.ics");
        ICalWriter writer = null;
        try {
            writer = new ICalWriter(file, ICalVersion.V2_0);
            writer.write(ical);
        } finally {
            if (writer != null) writer.close();
        }
        return file;
    }
    public static File createCancelICSFile(Instant startDate, Instant endDateTime, String meetingId,
                                           Map<ICSKey,String> req, Set<String> guests) throws Exception{
        if(startDate==null || endDateTime==null) throw new ApplicationException("Start end date is mandatory");
        ICalendar ical = new ICalendar();
        ical.addProperty(new Method(Method.CANCEL));
        VEvent event = new VEvent();
        if (req.containsKey(ICSKey.SUMMARY)) {
            event.setSummary(req.get(ICSKey.SUMMARY));
            event.getSummary().setLanguage("en-us");
        }
        if (req.containsKey(ICSKey.MEETING)) {
            event.setLocation(req.get(ICSKey.MEETING));
        }
        if (req.containsKey(ICSKey.DESCRIPTION)) {
            event.setDescription(req.get(ICSKey.DESCRIPTION));
        }
        if (req.containsKey(ICSKey.ORGANIZER)) {
            event.setOrganizer(req.get(ICSKey.ORGANIZER));
        }else{
            throw new ApplicationException("Organizer should not be empty");
        }
        if (CollectionUtils.isNotEmpty(guests)){
            guests.forEach(gus->{
                event.addAttendee(gus);
            });
        }else{
            throw new ApplicationException("Guest list should not be empty");
        }
        event.setSequence(10);
        event.setPriority(5);
        event.setDateStart(Date.from(startDate));
        event.setDateEnd(Date.from(endDateTime));
        event.setStatus(Status.cancelled());
        if (StringUtils.isEmpty(meetingId)) {
            event.setUid(CommonUtils.getEighteenDigitRandomString());
        } else {
            event.setUid(meetingId);
        }
        ical.addEvent(event);
        File file = new File("cancel.ics");
        ICalWriter writer = null;
        try {
            writer = new ICalWriter(file, ICalVersion.V2_0);
            writer.write(ical);
        } finally {
            if (writer != null) writer.close();
        }
        return file;

    }
    public enum ICSKey {
        SUMMARY, MEETING,DESCRIPTION,ORGANIZER,FREQUENCY, INTERVAL;
        // FREQUENCY value will be following.
        //SECONDLY,MINUTELY,HOURLY,DAILY,WEEKLY,MONTHLY,YEARLY;
        // Meeting id will be unique key which will get used for cancel the event
  }
  public static void main(String[] args) throws Exception {
        Map<ICSKey,String> map=new HashMap<ICSKey,String>();
        map.put(ICSKey.DESCRIPTION,"Testing app");
        map.put(ICSKey.SUMMARY,"Testing");
        map.put(ICSKey.MEETING,"https://www.eserve.co/meet");
        map.put(ICSKey.ORGANIZER,"vikashpandeyeng@gmail.com");
        Set<String> set=new HashSet<String>();
        set.add("vikasheng@outlook.com");
        set.add("vikashpandeyeng@gmail.com");
        Instant from=Instant.now().plus(5, ChronoUnit.HOURS);
        Instant to=Instant.now().plus(6, ChronoUnit.HOURS);
        String randomNumber="6FC52015-1267-4F8E-BC28-1D7AE55A7C94";
        File file=createICSFile(from,to,randomNumber,map,set);
        sendemail(file,"REQUEST","Created");
       // File file1=cancelcreateICSFile(from,to,randomNumber,map,set);
        //System.out.println(Instant.ofEpochMilli(1647527064913l));

        //sendemail(file1,"CANCEL","Cancel");
  }
    public static void sendemail(File file,String type,String text){
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
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("vikasheng@outlook.com"));
            message.setSubject("Discussion regarding product release");

            /*final DataSource iCalData = new ByteArrayDataSource(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(file.getPath()))), "text/calendar; charset=UTF-8");
            message.setDataHandler(new DataHandler(iCalData));
            message.setFileName(file.getName());
            message.setHeader("Content-Type", "text/calendar; charset=UTF-8; method=REQUEST");
*/
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("<a href=\"https://www.eserve.co/wm?vcsid=7292F18F48D1480387F924557EB053850C549F0DD11D468880F74671E24593E3&intents={Meeting_Action:CANCEL}&wsSid=111111111\" rel=\"help nofollow\">"+text+"</a>");
            messageBodyPart1.setHeader("Content-Type","text/html");

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            //DataSource source = new FileDataSource(file.getName());
            messageBodyPart2.addHeaderLine("method=REQUEST");
            messageBodyPart2.addHeaderLine("charset=UTF-8");
            messageBodyPart2.addHeaderLine("component=VEVENT");
            //messageBodyPart2.setHeader("Content-Type", "text/calendar; charset=UTF-8; method="+type);
            messageBodyPart2.attachFile(file);
            //messageBodyPart2.setDataHandler(new DataHandler(source));
           // messageBodyPart2.setFileName(file.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
