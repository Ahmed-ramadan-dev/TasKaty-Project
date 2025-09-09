package com.spring.boot.ServiceHelper;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTaskNotification(String toEmail, String taskTitle, String taskDescription, String managerName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("New Task: " + taskTitle);

            // نص الرسالة
            helper.setText("Hello,\n\nYou have a new task from " + managerName + ":\n\n" + taskDescription);

            // تغيير اسم المرسل
            helper.setFrom("mahmoudsabbahamr445@gmail.com", "TasKaty-App");

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*public void sendTaskNotification(String toEmail, String taskTitle, String taskDescription, String managerName) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("New Task: " + taskTitle);
    message.setText("Hello,\n\nYou have a new task from " + managerName + ":\n\n" + taskDescription);

    mailSender.send(message);*/
