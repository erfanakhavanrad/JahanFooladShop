package com.jahanfoolad.jfs.service.impl;

import jakarta.mail.BodyPart;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class EmailServices {

    @Value("${spring.mail.host}")
    String mailServer;

    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String mailServerPassword;

    @Value("${spring.mail.port}")
    String port;

    @Autowired
    public JavaMailSender emailSender;

    private final static String PASSWORD_SMS_TEXT = "";
    private final static String SUBJECT = "aska2z.com , Account info ";
    private final static String SUBJECT_CRM = "aska2z.com , CRM Information ";
    private String password ,title , description , project , status , user_name ,statusDesc;
    private String dbUserName;


    public String send(String to, String password) {
        try {
            log.info("--------------password in sending to : "+to+"  is  : "+password);
            Session session = Session.getInstance(initProperties());
            MimeMessage msg = new MimeMessage(session);

            msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            msg.addFrom(new InternetAddress[]{new InternetAddress(username)});
            msg.setSubject(SUBJECT_CRM, "UTF-8");
            setEmailText(password);
            msg.setText(getPasswordEmailText(), "UTF-8");

            Multipart contentPart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(getPasswordEmailText(), "text/html;charset=UTF-8");
            contentPart.addBodyPart(bodyPart);

            msg.setContent(contentPart);
            setTransporter(msg, session);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String sendCompany(String user_name ,String title ,String  description ,String  project ,String  status ,String to) {
        try {
            log.info("--------------comapny sending  title  is  : "+title);
            if(description == null)
                description = "بدون توضیحات ";
            log.info("--------------company sending  desc  is    : "+description);
            log.info("--------------company sending  project is  : "+project);
            log.info("--------------company sending  status  is  : "+status);
            log.info("--------------company sending  to : "+to);
            Session session = Session.getInstance(initProperties());
            MimeMessage msg = new MimeMessage(session);

            msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            msg.addFrom(new InternetAddress[]{new InternetAddress(username)});
            msg.setSubject(SUBJECT_CRM, "UTF-8");
//            setTaskEmailText(user_name,title,description,project,status,"");
            msg.setText(getCompanyAddEmailText(), "UTF-8");

            Multipart contentPart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(getCompanyAddEmailText(), "text/html;charset=UTF-8");
            contentPart.addBodyPart(bodyPart);

            msg.setContent(contentPart);
            setTransporter(msg, session);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    public String sendArticle(String user_name ,String title ,String  description ,String  project ,String  status ,String statusDesc , String to) {
        try {
            log.info("--------------article sending  title  is  : "+title);
            if(description == null) description = "بدون توضیحات ";
            if(statusDesc == null)  description = " ";
            log.info("--------------article sending  desc  is  : "+description);
            log.info("--------------article sending  project is  : "+project);
            log.info("--------------article sending  status  is  : "+status);
            log.info("--------------article sending  status desc  is  : "+statusDesc);
            log.info("--------------article sending  to : "+to);
            Session session = Session.getInstance(initProperties());
            MimeMessage msg = new MimeMessage(session);

            msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            msg.addFrom(new InternetAddress[]{new InternetAddress(username)});
            msg.setSubject(SUBJECT_CRM, "UTF-8");
//            setTaskEmailText(user_name,title,description,project,status ,statusDesc);
            msg.setText(getTaskEmailText(), "UTF-8");

            Multipart contentPart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(getArticleEmailText(), "text/html;charset=UTF-8");
            contentPart.addBodyPart(bodyPart);

            msg.setContent(contentPart);
            setTransporter(msg, session);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public Properties initProperties() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", mailServer);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", true);
        return properties;
    }

    public void setTransporter(MimeMessage msg, Session session) throws Exception {
        Transport transport = session.getTransport();
        transport.connect(username, mailServerPassword);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    public String getPasswordEmailText() {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"" +
                "      xmlns:o=\"urn:schemas-microsoft-com:office:office\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary -->" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <!-- Use the latest (edge) version of IE rendering engine -->" +
                "    <meta name=\"x-apple-disable-message-reformatting\">  <!-- Disable auto-scale in iOS 10 Mail entirely -->" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700\" rel=\"stylesheet\">" +
                "    <title>Account password</title>" +
                "" +
                "    <style>" +
                "        html," +
                "        body {" +
                "            margin: 0 auto !important;" +
                "            padding: 0 !important;" +
                "            height: 100% !important;" +
                "            width: 100% !important;" +
                "            background: #f1f1f1;" +
                "        }" +
                "" +
                "        /* What it does: Stops email clients resizing small text. */" +
                "        * {" +
                "            -ms-text-size-adjust: 100%;" +
                "            -webkit-text-size-adjust: 100%;" +
                "        }" +
                "" +
                "        /* What it does: Centers email on Android 4.4 */" +
                "        div[style*=\"margin: 16px 0\"] {" +
                "            margin: 0 !important;" +
                "        }" +
                "" +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */" +
                "        table," +
                "        td {" +
                "            mso-table-lspace: 0pt !important;" +
                "            mso-table-rspace: 0pt !important;" +
                "        }" +
                "" +
                "        /* What it does: Fixes webkit padding issue. */" +
                "        table {" +
                "            border-spacing: 0 !important;" +
                "            border-collapse: collapse !important;" +
                "            table-layout: fixed !important;" +
                "            margin: 0 auto !important;" +
                "        }" +
                "" +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */" +
                "        img {" +
                "            -ms-interpolation-mode: bicubic;" +
                "        }" +
                "" +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */" +
                "        a {" +
                "            text-decoration: none;" +
                "        }" +
                "" +
                "        /* What it does: A work-around for email clients meddling in triggered links. */" +
                "        *[x-apple-data-detectors], /* iOS */" +
                "        .unstyle-auto-detected-links *," +
                "        .aBn {" +
                "            border-bottom: 0 !important;" +
                "            cursor: default !important;" +
                "            color: inherit !important;" +
                "            text-decoration: none !important;" +
                "            font-size: inherit !important;" +
                "            font-family: inherit !important;" +
                "            font-weight: inherit !important;" +
                "            line-height: inherit !important;" +
                "        }" +
                "" +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */" +
                "        .a6S {" +
                "            display: none !important;" +
                "            opacity: 0.01 !important;" +
                "        }" +
                "" +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */" +
                "        .im {" +
                "            color: inherit !important;" +
                "        }" +
                "" +
                "        /* If the above doesn't work, add a .g-img class to any image in question. */" +
                "        img.g-img + div {" +
                "            display: none !important;" +
                "        }" +
                "" +
                "        /* What it does: Removes right gutter in Gmail iOS app: https://github.com/TedGoas/Cerberus/issues/89  */" +
                "        /* Create one of these media queries for each additional viewport size you'd like to fix */" +
                "" +
                "        /* iPhone 4, 4S, 5, 5S, 5C, and 5SE */" +
                "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) {" +
                "            u ~ div .email-container {" +
                "                min-width: 320px !important;" +
                "            }" +
                "        }" +
                "" +
                "        /* iPhone 6, 6S, 7, 8, and X */" +
                "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) {" +
                "            u ~ div .email-container {" +
                "                min-width: 375px !important;" +
                "            }" +
                "        }" +
                "" +
                "        /* iPhone 6+, 7+, and 8+ */" +
                "        @media only screen and (min-device-width: 414px) {" +
                "            u ~ div .email-container {" +
                "                min-width: 414px !important;" +
                "            }" +
                "        }" +
                "" +
                "        /* Reset */" +
                "        .bg_white {" +
                "            background: #ffffff;" +
                "        }" +
                "" +
                "        h1, h2, h3, h4, h5, h6 {" +
                "            font-family: 'Lato', sans-serif;" +
                "            color: #000000;" +
                "            margin-top: 0;" +
                "            font-weight: 400;" +
                "        }" +
                "" +
                "        body {" +
                "            font-family: 'Lato', sans-serif;" +
                "            font-weight: 400;" +
                "            font-size: 15px;" +
                "            line-height: 1.8;" +
                "            color: rgba(0, 0, 0, .4);" +
                "        }" +
                "" +
                "        /*LOGO*/" +
                "" +
                "        .logo h1 {" +
                "            margin: 0;" +
                "        }" +
                "" +
                "        .logo h1 a {" +
                "            color: #30e3ca;" +
                "            font-size: 24px;" +
                "            font-weight: 700;" +
                "            font-family: 'Lato', sans-serif;" +
                "        }" +
                "" +
                "        /*HERO*/" +
                "        .hero {" +
                "            position: relative;" +
                "            z-index: 0;" +
                "        }" +
                "" +
                "        .hero .text {" +
                "            color: rgba(0, 0, 0, .3);" +
                "        }" +
                "" +
                "        .hero .text h2 {" +
                "            color: #555;" +
                "            font-size: 27px;" +
                "            margin-bottom: 0;" +
                "            font-weight: bold;" +
                "            line-height: 1.4;" +
                "        }" +
                "" +
                "        .hero .text h3 {" +
                "            color: #999;" +
                "            font-size: 16px;" +
                "            margin-top: 1em;" +
                "            margin-bottom: 0;" +
                "            line-height: 1.4;" +
                "        }" +
                "" +
                "        .hero .text h2 span {" +
                "            font-weight: 600;" +
                "            color: #30e3ca;" +
                "        }" +
                "" +
                "        .password-container {" +
                "            width: 200px;" +
                "            padding: 30px;" +
                "            background-color: #dcfae4;" +
                "            border: solid 2px #b3e7c3;" +
                "            border-radius: 10px;" +
                "            margin: 50px auto;" +
                "            justify-content: center;" +
                "            font-size: 30px;" +
                "            font-weight: bold;" +
                "            color: #3f8756;" +
                "        }" +
                "" +
                "        .blue-dark-gradient {" +
                "            color:#a0479c !important;" +
                "            line-height: initial !important;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f1f1f1;\">" +
                "<center style=\"width: 100%; background-color: #f1f1f1;\">" +
                "    <div style=\"display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">" +
                "        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;" +
                "    </div>" +
                "    <div style=\"max-width: 800px; margin: 0 auto;\" class=\"email-container\">" +
                "        <!-- BEGIN BODY -->" +
                "        <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"" +
                "               style=\"margin: auto;\">" +
                "            <tr>" +
                "                <td valign=\"top\" class=\"bg_white\" style=\"padding: 1em 2.5em 0 2.5em;\">" +
                "                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-bottom: solid 1px #f1f1f1;\">" +
                "                        <tr>" +
                "                            <td class=\"logo\" style=\"text-align: center;\">" +
                "                                <h1 class=\"no-margin\">" +
                "                                    <a href=\"https://aska2z.com\" target=\"_blank\" class=\"blue-dark-gradient\">" +
                "                                        AskA2Z.com" +
                "                                    </a>" +
                "                                </h1>" +
                "                            </td>" +
                "                        </tr>" +
                "                    </table>" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 3em 0 2em 0;\">" +
                "                    <img src=\"https://aska2z.com/files/images/email.png\" alt=\"\"" +
                "                         style=\"width: 175px; max-width: 175px; height: auto; margin: auto; display: block;\">" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 1em 0;\">" +
                "                    <table>" +
                "                        <tr>" +
                "                            <td>" +
                "                                <div class=\"text\" style=\"padding: 0 .5em; text-align: center;\">" +
                "                                    <h2>Your account password</h2>" +
                "                                </div>" +
                "                            </td>" +
                "                        </tr>" +
                "                    </table>" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "            <!-- 1 Column Text + Button : END -->" +
                "" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 4em 0;\">" +
                "                    <table>" +
                "                        <tr>" +
                "                            <td>" +
                "                                <div class=\"password-container\" style=\"padding: .5em 1em; text-align: center;\">" + password + " </div>" +
                "                            </td>" +
                "                        </tr>" +
                "                    </table>" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "" +
                "" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\">" +
                "                    <div class=\"text\" style=\"padding: 0 3em; text-align: center;\">" +
                "                        <p style=\"margin: 0 0 1em 0\">" + "For any questions you may have," +
                "                            please do not hesitate to email us at support@aska2z.com" +
                "                            Best regards," +
                "                        </p>" +
                "                    </div>" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\">" +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\">" +
                "                        <p style=\"margin: 0;\">Irvine, California.</p>" +
                "                    </div>" +
                "                </td>" +
                "            </tr><!-- end tr -->" +
                "            <tr>" +
                "                <td valign=\"middle\" class=\"hero bg_white\">" +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\">" +
                "                        <p style=\"margin: 0 0 1em 0\">" +
                "                            &copy; <a href=\"https://aska2z.com\" target=\"_blank\" class=\"blue-dark-gradient\">AskA2Z.com</a>." +
                "                            All rights reserved." +
                "                        </p>" +
                "                    </div>" +
                "                </td>" +
                "            </tr>" +
                "        </table>" +
                "    </div>" +
                "</center>" +
                "</body>" +
                "</html>";
    }

    public void setEmailText(String password) {
        this.password = password;
    }

    public void setTaskEmailText(String from , String subject , String description , String dbUserName){
        this.title = subject;
        this.description = description;
        this.user_name = from;
        this.dbUserName = dbUserName;
    }

    public String getTaskEmailText()   {
        return "<!DOCTYPE html> " +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" " +
                "      xmlns:o=\"urn:schemas-microsoft-com:office:office\"> " +
                "<head> " +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary --> " +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <!-- Use the latest (edge) version of IE rendering engine --> " +
                "    <meta name=\"x-apple-disable-message-reformatting\">  <!-- Disable auto-scale in iOS 10 Mail entirely --> " +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700\" rel=\"stylesheet\"> " +
                "    <title>Jahan Foolad Information</title> " +
                " " +
                "    <style> " +
                "        html, " +
                "        body { " +
                "            margin: 0 auto !important; " +
                "            padding: 0 !important; " +
                "            height: 100% !important; " +
                "            width: 100% !important; " +
                "            background: #f1f1f1; " +
                "        } " +
                " " +
                "        /* What it does: Stops email clients resizing small text. */ " +
                "        * { " +
                "            -ms-text-size-adjust: 100%; " +
                "            -webkit-text-size-adjust: 100%; " +
                "        } " +
                " " +
                "        /* What it does: Centers email on Android 4.4 */ " +
                "        div[style*=\"margin: 16px 0\"] { " +
                "            margin: 0 !important; " +
                "        } " +
                " " +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */ " +
                "        table, " +
                "        td { " +
                "            mso-table-lspace: 0pt !important; " +
                "            mso-table-rspace: 0pt !important; " +
                "        } " +
                " " +
                "        /* What it does: Fixes webkit padding issue. */ " +
                "        table { " +
                "            border-spacing: 0 !important; " +
                "            border-collapse: collapse !important; " +
                "            table-layout: fixed !important; " +
                "            margin: 0 auto !important; " +
                "        } " +
                " " +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */ " +
                "        img { " +
                "            -ms-interpolation-mode: bicubic; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */ " +
                "        a { " +
                "            text-decoration: none; " +
                "        } " +
                " " +
                "        /* What it does: A work-around for email clients meddling in triggered links. */ " +
                "        *[x-apple-data-detectors], /* iOS */ " +
                "        .unstyle-auto-detected-links *, " +
                "        .aBn { " +
                "            border-bottom: 0 !important; " +
                "            cursor: default !important; " +
                "            color: inherit !important; " +
                "            text-decoration: none !important; " +
                "            font-size: inherit !important; " +
                "            font-family: inherit !important; " +
                "            font-weight: inherit !important; " +
                "            line-height: inherit !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */ " +
                "        .a6S { " +
                "            display: none !important; " +
                "            opacity: 0.01 !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */ " +
                "        .im { " +
                "            color: inherit !important; " +
                "        } " +
                " " +
                "        /* If the above doesn't work, add a .g-img class to any image in question. */ " +
                "        img.g-img + div { " +
                "            display: none !important; " +
                "        } " +
                " " +
                "        /* What it does: Removes right gutter in Gmail iOS app: https://github.com/TedGoas/Cerberus/issues/89  */ " +
                "        /* Create one of these media queries for each additional viewport size you'd like to fix */ " +
                " " +
                "        /* iPhone 4, 4S, 5, 5S, 5C, and 5SE */ " +
                "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 320px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6, 6S, 7, 8, and X */ " +
                "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 375px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6+, 7+, and 8+ */ " +
                "        @media only screen and (min-device-width: 414px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 414px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* Reset */ " +
                "        .bg_white { " +
                "            background: #ffffff; " +
                "        } " +
                " " +
                "        h1, h2, h3, h4, h5, h6 { " +
                "            font-family: 'Lato', sans-serif; " +
                "            color: #000000; " +
                "            margin-top: 0; " +
                "            font-weight: 400; " +
                "        } " +
                " " +
                "        body { " +
                "            font-family: 'Lato', sans-serif; " +
                "            font-weight: 400; " +
                "            font-size: 15px; " +
                "            line-height: 1.8; " +
                "            color: rgba(0, 0, 0, .4); " +
                "        } " +
                " " +
                "        /*LOGO*/ " +
                " " +
                "        .logo h1 { " +
                "            margin: 0; " +
                "        } " +
                " " +
                "        .logo h1 a { " +
                "            color: #30e3ca; " +
                "            font-size: 24px; " +
                "            font-weight: 700; " +
                "            font-family: 'Lato', sans-serif; " +
                "        } " +
                " " +
                "        /*HERO*/ " +
                "        .hero { " +
                "            position: relative; " +
                "            z-index: 0; " +
                "        } " +
                " " +
                "        .hero .text { " +
                "            color: rgba(0, 0, 0, .3); " +
                "        } " +
                " " +
                "        .hero .text h2 { " +
                "            color: #555; " +
                "            font-size: 27px; " +
                "            margin-bottom: 0; " +
                "            font-weight: bold; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h3 { " +
                "            color: #999; " +
                "            font-size: 16px; " +
                "            margin-top: 1em; " +
                "            margin-bottom: 0; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h2 span { " +
                "            font-weight: 600; " +
                "            color: #30e3ca; " +
                "        } " +
                " " +
                "        .password-container { " +
                "            width: 200px; " +
                "            padding: 30px; " +
                "            background-color: #dcfae4; " +
                "            border: solid 2px #b3e7c3; " +
                "            border-radius: 10px; " +
                "            margin: 50px auto; " +
                "            justify-content: center; " +
                "            font-size: 30px; " +
                "            font-weight: bold; " +
                "            color: #3f8756; " +
                "        } " +
                " " +
                "        .sedri-color { " +
                "            background: #456543; " +
                "            -webkit-background-clip: text; " +
                "            -webkit-text-fill-color: transparent; " +
                "            -moz-text-fill-color: transparent; " +
                "            background-size: 100%; " +
                "            line-height: initial !important; " +
                "        } " +
                " " +
                "        .no-image { " +
                "            width: 30px; " +
                "            height: 30px; " +
                "            border-radius: 100%; " +
                "            float: left; " +
                "            margin-right: 10px; " +
                "        } " +
                " " +
                "        .button { " +
                "            padding: 0 15px; " +
                "            height: 30px; " +
                "            border-radius: 30px; " +
                "            line-height: 27px; " +
                "            font-weight: bold; " +
                "            float: left; " +
                "            margin: 0 10px 10px 0; " +
                "            color: #ffffff; " +
                "        } " +
                "    </style> " +
                "</head> " +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f1f1f1;\"> " +
                "<center style=\"width: 100%; background-color: #f1f1f1;\"> " +
                "    <div style=\"display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\"> " +
                "        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp; " +
                "    </div> " +
                "    <div style=\"max-width: 800px; margin: 0 auto;\" class=\"email-container\"> " +
                "        <!-- BEGIN BODY --> " +
                "        <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" " +
                "               style=\"margin: auto;\"> " +
                "            <tr> " +
                "                <td valign=\"top\" class=\"bg_white\" style=\"padding: 1em 2.5em 0 2.5em;\"> " +
                "                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-bottom: solid 1px #f1f1f1;\"> " +
                "                        <tr> " +
                "                            <td class=\"logo\" style=\"text-align: center;\"> " +
                "                                <h1 class=\"no-margin\"> " +
                "                                    <a href=\"https://JahanFoolad.com/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\"> " +
                "                                        Jahan Foolad Info " +
                "                                    </a> " +
                "                                </h1> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 1em 0;\"> " +
                "                    <table style=\"width: 500px\"> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div class=\"text\" style=\"width: 100%; height: 65px; border-bottom: solid 1px #DDD;\"> " +
                "                                    <img src=\"https://JahanFoolad.com/assets/images/logo/JahanFoolad.png\" alt=\"\" " +
                "                                         class=\"no-image\" style=\"border-radius: 100%; float: left; margin-right: 10px; margin-top: 10px; width: 35px;height: 35px;\"> " +
                "                                    <div style=\"float: left; padding-left: 10px;\"> " +
                "                                        <h2 style=\"font-size: 20px;\">  Support needed from  : " +user_name+
                " " +
                "                                            <br> " +
                " " +
                "                                        </h2> " +
                "                                    </div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div class=\"text\" style=\"width: 100%; height: 65px; border-bottom: solid 1px #DDD;\"> " +
                "                                    <div style=\"float: left; padding-left: 10px;\"> " +
                "                                        <h2 style=\"font-size: 20px;\">  Subject  : " +title+
                " " +
                "                                            <br> " +
                " " +
                "                                        </h2> " +
                "                                    </div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task description --> " +
                "                                <div dir=\"rtl\" style=\"font-size: 17px; font-weight: normal;\"> Description : " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task description --> " +
                "                                <div dir=\"ltr\" style=\"font-size: 17px; font-weight: normal; \"> " +description+
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <h3 style=\"border-bottom: solid 1px #dddddd;margin-top: 40px\">Project:</h3> " +
                " " +
                "                                <div> " +
                "                                    <div style=\"width: 12px; height: 12px; border-radius: 3px; background-color: #be8108; float: left\"></div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0;\">No. 24, 16w, kaj Sq, Sa'adat Abad, Tehran, Iran.</p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0 0 1em 0\"> " +
                "                            &copy; <a href=\"https://JahanFoolad.com/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\">Jahan Foolad</a>. " +
                "                            All rights reserved. " +
                "                        </p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "        </table> " +
                "    </div> " +
                "</center> " +
                "</body> " +
                "</html>";
    }

    public String getCompanyAddEmailText()   {
        return "<!DOCTYPE html> " +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" " +
                "      xmlns:o=\"urn:schemas-microsoft-com:office:office\"> " +
                "<head> " +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary --> " +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <!-- Use the latest (edge) version of IE rendering engine --> " +
                "    <meta name=\"x-apple-disable-message-reformatting\">  <!-- Disable auto-scale in iOS 10 Mail entirely --> " +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700\" rel=\"stylesheet\"> " +
                "    <title>CRM Information</title> " +
                " " +
                "    <style> " +
                "        html, " +
                "        body { " +
                "            margin: 0 auto !important; " +
                "            padding: 0 !important; " +
                "            height: 100% !important; " +
                "            width: 100% !important; " +
                "            background: #f1f1f1; " +
                "        } " +
                " " +
                "        /* What it does: Stops email clients resizing small text. */ " +
                "        * { " +
                "            -ms-text-size-adjust: 100%; " +
                "            -webkit-text-size-adjust: 100%; " +
                "        } " +
                " " +
                "        /* What it does: Centers email on Android 4.4 */ " +
                "        div[style*=\"margin: 16px 0\"] { " +
                "            margin: 0 !important; " +
                "        } " +
                " " +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */ " +
                "        table, " +
                "        td { " +
                "            mso-table-lspace: 0pt !important; " +
                "            mso-table-rspace: 0pt !important; " +
                "        } " +
                " " +
                "        /* What it does: Fixes webkit padding issue. */ " +
                "        table { " +
                "            border-spacing: 0 !important; " +
                "            border-collapse: collapse !important; " +
                "            table-layout: fixed !important; " +
                "            margin: 0 auto !important; " +
                "        } " +
                " " +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */ " +
                "        img { " +
                "            -ms-interpolation-mode: bicubic; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */ " +
                "        a { " +
                "            text-decoration: none; " +
                "        } " +
                " " +
                "        /* What it does: A work-around for email clients meddling in triggered links. */ " +
                "        *[x-apple-data-detectors], /* iOS */ " +
                "        .unstyle-auto-detected-links *, " +
                "        .aBn { " +
                "            border-bottom: 0 !important; " +
                "            cursor: default !important; " +
                "            color: inherit !important; " +
                "            text-decoration: none !important; " +
                "            font-size: inherit !important; " +
                "            font-family: inherit !important; " +
                "            font-weight: inherit !important; " +
                "            line-height: inherit !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */ " +
                "        .a6S { " +
                "            display: none !important; " +
                "            opacity: 0.01 !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */ " +
                "        .im { " +
                "            color: inherit !important; " +
                "        } " +
                " " +
                "        /* If the above doesn't work, add a .g-img class to any image in question. */ " +
                "        img.g-img + div { " +
                "            display: none !important; " +
                "        } " +
                " " +
                "        /* What it does: Removes right gutter in Gmail iOS app: https://github.com/TedGoas/Cerberus/issues/89  */ " +
                "        /* Create one of these media queries for each additional viewport size you'd like to fix */ " +
                " " +
                "        /* iPhone 4, 4S, 5, 5S, 5C, and 5SE */ " +
                "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 320px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6, 6S, 7, 8, and X */ " +
                "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 375px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6+, 7+, and 8+ */ " +
                "        @media only screen and (min-device-width: 414px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 414px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* Reset */ " +
                "        .bg_white { " +
                "            background: #ffffff; " +
                "        } " +
                " " +
                "        h1, h2, h3, h4, h5, h6 { " +
                "            font-family: 'Lato', sans-serif; " +
                "            color: #000000; " +
                "            margin-top: 0; " +
                "            font-weight: 400; " +
                "        } " +
                " " +
                "        body { " +
                "            font-family: 'Lato', sans-serif; " +
                "            font-weight: 400; " +
                "            font-size: 15px; " +
                "            line-height: 1.8; " +
                "            color: rgba(0, 0, 0, .4); " +
                "        } " +
                " " +
                "        /*LOGO*/ " +
                " " +
                "        .logo h1 { " +
                "            margin: 0; " +
                "        } " +
                " " +
                "        .logo h1 a { " +
                "            color: #30e3ca; " +
                "            font-size: 24px; " +
                "            font-weight: 700; " +
                "            font-family: 'Lato', sans-serif; " +
                "        } " +
                " " +
                "        /*HERO*/ " +
                "        .hero { " +
                "            position: relative; " +
                "            z-index: 0; " +
                "        } " +
                " " +
                "        .hero .text { " +
                "            color: rgba(0, 0, 0, .3); " +
                "        } " +
                " " +
                "        .hero .text h2 { " +
                "            color: #555; " +
                "            font-size: 27px; " +
                "            margin-bottom: 0; " +
                "            font-weight: bold; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h3 { " +
                "            color: #999; " +
                "            font-size: 16px; " +
                "            margin-top: 1em; " +
                "            margin-bottom: 0; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h2 span { " +
                "            font-weight: 600; " +
                "            color: #30e3ca; " +
                "        } " +
                " " +
                "        .password-container { " +
                "            width: 200px; " +
                "            padding: 30px; " +
                "            background-color: #dcfae4; " +
                "            border: solid 2px #b3e7c3; " +
                "            border-radius: 10px; " +
                "            margin: 50px auto; " +
                "            justify-content: center; " +
                "            font-size: 30px; " +
                "            font-weight: bold; " +
                "            color: #3f8756; " +
                "        } " +
                " " +
                "        .sedri-color { " +
                "            background: #456543; " +
                "            -webkit-background-clip: text; " +
                "            -webkit-text-fill-color: transparent; " +
                "            -moz-text-fill-color: transparent; " +
                "            background-size: 100%; " +
                "            line-height: initial !important; " +
                "        } " +
                " " +
                "        .no-image { " +
                "            width: 30px; " +
                "            height: 30px; " +
                "            border-radius: 100%; " +
                "            float: left; " +
                "            margin-right: 10px; " +
                "        } " +
                " " +
                "        .button { " +
                "            padding: 0 15px; " +
                "            height: 30px; " +
                "            border-radius: 30px; " +
                "            line-height: 27px; " +
                "            font-weight: bold; " +
                "            float: left; " +
                "            margin: 0 10px 10px 0; " +
                "            color: #ffffff; " +
                "        } " +
                "    </style> " +
                "</head> " +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f1f1f1;\"> " +
                "<center style=\"width: 100%; background-color: #f1f1f1;\"> " +
                "    <div style=\"display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\"> " +
                "        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp; " +
                "    </div> " +
                "    <div style=\"max-width: 800px; margin: 0 auto;\" class=\"email-container\"> " +
                "        <!-- BEGIN BODY --> " +
                "        <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" " +
                "               style=\"margin: auto;\"> " +
                "            <tr> " +
                "                <td valign=\"top\" class=\"bg_white\" style=\"padding: 1em 2.5em 0 2.5em;\"> " +
                "                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-bottom: solid 1px #f1f1f1;\"> " +
                "                        <tr> " +
                "                            <td class=\"logo\" style=\"text-align: center;\"> " +
                "                                <h1 class=\"no-margin\"> " +
                "                                    <a href=\"http://dabacrm.ir/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\"> " +
                "                                        Daba CRM " +
                "                                    </a> " +
                "                                </h1> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 1em 0;\"> " +
                "                    <table style=\"width: 500px\"> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div class=\"text\" style=\"width: 100%; height: 65px; border-bottom: solid 1px #DDD;\"> " +
                "                                    <img src=\"https://aska2z.com/files/images/no-person-image.jpeg\" alt=\"\" " +
                "                                         class=\"no-image\" style=\"border-radius: 100%; float: left; margin-right: 10px; margin-top: 10px; width: 35px;height: 35px;\"> " +
                "                                    <div style=\"float: left; padding-left: 10px;\"> " +
                "                                        <h2 style=\"font-size: 20px;\">  Dear " +
                "                                            "+user_name+", a company added " +
                " " +
                "                                            <br> " +
                " " +
                "                                            <a href=\"http://dabacrm.ir/\" target=\"_blank\" " +
                "                                               style=\"color: #39b293; font-size: 16px; text-decoration: none !important\"> " +
                "                                                View in Dashboard " +
                "                                            </a> " +
                "                                        </h2> " +
                "                                    </div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task name --> " +
                "                                <div dir=\"rtl\"  charset=\"utf-8\" style=\"font-size: 20px; font-weight: bold; margin-top: 40px; color: #555;\"> company name : "+title+"</div>" +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task description --> " +
                "                                <div dir=\"rtl\" style=\"font-size: 15px; font-weight: normal; color: #999;\"> " +description+
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task name --> " +
                "                                <div dir=\"rtl\"  charset=\"utf-8\" style=\"font-size: 12px; font-weight: normal; margin-top: 40px; color: #555;\"> وضعیت حال حاضر:</div>" +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div style=\"margin-top: 10px;\"> " +
                "                                    <!-- printed based on status --> " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("unknown") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #4c1b91;\">"+(status.equalsIgnoreCase("UNKNOWN") ? "UNKNOWN" : "")+"</div> " +

                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("company_closed") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; width:0px!important visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #b42e2e;\">"+ (status.equalsIgnoreCase("company_closed") ? "COMPANY CLOSED" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("not_response") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #4c1b91;\">"+(status.equalsIgnoreCase("not_response") ? "NOT RESPOND" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("pending") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #555555; background-color: #fbe36d;\">"+(status.equalsIgnoreCase("pending") ? "PENDING" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("thinking") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #a6ab58;\">"+(status.equalsIgnoreCase("thinking") ? "THINKING" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("sold_successfully") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #1e811b;\">"+(status.equalsIgnoreCase("SOLD SUCCESSFULLY") ? "sold_successfully" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("not_interested") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #4d4d4d;\">"+(status.equalsIgnoreCase("NOT INTERESTED") ? "not_interested" : "")+"</div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <h3 style=\"border-bottom: solid 1px #dddddd;margin-top: 40px\">Project:</h3> " +
                " " +
                "                                <div> " +
                "                                    <div style=\"width: 12px; height: 12px; border-radius: 3px; background-color: #be8108; float: left\"></div> " +
                " " +
                "                                    <span style=\"float: left; height: 12px; line-height: 12px; margin-left: 10px\">"+project+"</span> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0;\">No. 24, 16w, kaj Sq, Sa'adat Abad, Tehran, Iran.</p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0 0 1em 0\"> " +
                "                            &copy; <a href=\"http://dabacrm.ir/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\">Daba CRM</a>. " +
                "                            All rights reserved. " +
                "                        </p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "        </table> " +
                "    </div> " +
                "</center> " +
                "</body> " +
                "</html>";
    }

    public String getArticleEmailText()   {
        return "<!DOCTYPE html> " +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" " +
                "      xmlns:o=\"urn:schemas-microsoft-com:office:office\"> " +
                "<head> " +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary --> " +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <!-- Use the latest (edge) version of IE rendering engine --> " +
                "    <meta name=\"x-apple-disable-message-reformatting\">  <!-- Disable auto-scale in iOS 10 Mail entirely --> " +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700\" rel=\"stylesheet\"> " +
                "    <title>CRM Information</title> " +
                " " +
                "    <style> " +
                "        html, " +
                "        body { " +
                "            margin: 0 auto !important; " +
                "            padding: 0 !important; " +
                "            height: 100% !important; " +
                "            width: 100% !important; " +
                "            background: #f1f1f1; " +
                "        } " +
                " " +
                "        /* What it does: Stops email clients resizing small text. */ " +
                "        * { " +
                "            -ms-text-size-adjust: 100%; " +
                "            -webkit-text-size-adjust: 100%; " +
                "        } " +
                " " +
                "        /* What it does: Centers email on Android 4.4 */ " +
                "        div[style*=\"margin: 16px 0\"] { " +
                "            margin: 0 !important; " +
                "        } " +
                " " +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */ " +
                "        table, " +
                "        td { " +
                "            mso-table-lspace: 0pt !important; " +
                "            mso-table-rspace: 0pt !important; " +
                "        } " +
                " " +
                "        /* What it does: Fixes webkit padding issue. */ " +
                "        table { " +
                "            border-spacing: 0 !important; " +
                "            border-collapse: collapse !important; " +
                "            table-layout: fixed !important; " +
                "            margin: 0 auto !important; " +
                "        } " +
                " " +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */ " +
                "        img { " +
                "            -ms-interpolation-mode: bicubic; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */ " +
                "        a { " +
                "            text-decoration: none; " +
                "        } " +
                " " +
                "        /* What it does: A work-around for email clients meddling in triggered links. */ " +
                "        *[x-apple-data-detectors], /* iOS */ " +
                "        .unstyle-auto-detected-links *, " +
                "        .aBn { " +
                "            border-bottom: 0 !important; " +
                "            cursor: default !important; " +
                "            color: inherit !important; " +
                "            text-decoration: none !important; " +
                "            font-size: inherit !important; " +
                "            font-family: inherit !important; " +
                "            font-weight: inherit !important; " +
                "            line-height: inherit !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */ " +
                "        .a6S { " +
                "            display: none !important; " +
                "            opacity: 0.01 !important; " +
                "        } " +
                " " +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */ " +
                "        .im { " +
                "            color: inherit !important; " +
                "        } " +
                " " +
                "        /* If the above doesn't work, add a .g-img class to any image in question. */ " +
                "        img.g-img + div { " +
                "            display: none !important; " +
                "        } " +
                " " +
                "        /* What it does: Removes right gutter in Gmail iOS app: https://github.com/TedGoas/Cerberus/issues/89  */ " +
                "        /* Create one of these media queries for each additional viewport size you'd like to fix */ " +
                " " +
                "        /* iPhone 4, 4S, 5, 5S, 5C, and 5SE */ " +
                "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 320px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6, 6S, 7, 8, and X */ " +
                "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 375px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* iPhone 6+, 7+, and 8+ */ " +
                "        @media only screen and (min-device-width: 414px) { " +
                "            u ~ div .email-container { " +
                "                min-width: 414px !important; " +
                "            } " +
                "        } " +
                " " +
                "        /* Reset */ " +
                "        .bg_white { " +
                "            background: #ffffff; " +
                "        } " +
                " " +
                "        h1, h2, h3, h4, h5, h6 { " +
                "            font-family: 'Lato', sans-serif; " +
                "            color: #000000; " +
                "            margin-top: 0; " +
                "            font-weight: 400; " +
                "        } " +
                " " +
                "        body { " +
                "            font-family: 'Lato', sans-serif; " +
                "            font-weight: 400; " +
                "            font-size: 15px; " +
                "            line-height: 1.8; " +
                "            color: rgba(0, 0, 0, .4); " +
                "        } " +
                " " +
                "        /*LOGO*/ " +
                " " +
                "        .logo h1 { " +
                "            margin: 0; " +
                "        } " +
                " " +
                "        .logo h1 a { " +
                "            color: #30e3ca; " +
                "            font-size: 24px; " +
                "            font-weight: 700; " +
                "            font-family: 'Lato', sans-serif; " +
                "        } " +
                " " +
                "        /*HERO*/ " +
                "        .hero { " +
                "            position: relative; " +
                "            z-index: 0; " +
                "        } " +
                " " +
                "        .hero .text { " +
                "            color: rgba(0, 0, 0, .3); " +
                "        } " +
                " " +
                "        .hero .text h2 { " +
                "            color: #555; " +
                "            font-size: 27px; " +
                "            margin-bottom: 0; " +
                "            font-weight: bold; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h3 { " +
                "            color: #999; " +
                "            font-size: 16px; " +
                "            margin-top: 1em; " +
                "            margin-bottom: 0; " +
                "            line-height: 1.4; " +
                "        } " +
                " " +
                "        .hero .text h2 span { " +
                "            font-weight: 600; " +
                "            color: #30e3ca; " +
                "        } " +
                " " +
                "        .password-container { " +
                "            width: 200px; " +
                "            padding: 30px; " +
                "            background-color: #dcfae4; " +
                "            border: solid 2px #b3e7c3; " +
                "            border-radius: 10px; " +
                "            margin: 50px auto; " +
                "            justify-content: center; " +
                "            font-size: 30px; " +
                "            font-weight: bold; " +
                "            color: #3f8756; " +
                "        } " +
                " " +
                "        .sedri-color { " +
                "            background: #456543; " +
                "            -webkit-background-clip: text; " +
                "            -webkit-text-fill-color: transparent; " +
                "            -moz-text-fill-color: transparent; " +
                "            background-size: 100%; " +
                "            line-height: initial !important; " +
                "        } " +
                " " +
                "        .no-image { " +
                "            width: 30px; " +
                "            height: 30px; " +
                "            border-radius: 100%; " +
                "            float: left; " +
                "            margin-right: 10px; " +
                "        } " +
                " " +
                "        .button { " +
                "            padding: 0 15px; " +
                "            height: 30px; " +
                "            border-radius: 30px; " +
                "            line-height: 27px; " +
                "            font-weight: bold; " +
                "            float: left; " +
                "            margin: 0 10px 10px 0; " +
                "            color: #ffffff; " +
                "        } " +
                "    </style> " +
                "</head> " +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f1f1f1;\"> " +
                "<center style=\"width: 100%; background-color: #f1f1f1;\"> " +
                "    <div style=\"display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\"> " +
                "        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp; " +
                "    </div> " +
                "    <div style=\"max-width: 800px; margin: 0 auto;\" class=\"email-container\"> " +
                "        <!-- BEGIN BODY --> " +
                "        <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" " +
                "               style=\"margin: auto;\"> " +
                "            <tr> " +
                "                <td valign=\"top\" class=\"bg_white\" style=\"padding: 1em 2.5em 0 2.5em;\"> " +
                "                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-bottom: solid 1px #f1f1f1;\"> " +
                "                        <tr> " +
                "                            <td class=\"logo\" style=\"text-align: center;\"> " +
                "                                <h1 class=\"no-margin\"> " +
                "                                    <a href=\"http://dabacrm.ir/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\"> " +
                "                                        Daba CRM " +
                "                                    </a> " +
                "                                </h1> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 1em 0;\"> " +
                "                    <table style=\"width: 500px\"> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div class=\"text\" style=\"width: 100%; height: 65px; border-bottom: solid 1px #DDD;\"> " +
                "                                    <img src=\"https://aska2z.com/files/images/no-person-image.jpeg\" alt=\"\" " +
                "                                         class=\"no-image\" style=\"border-radius: 100%; float: left; margin-right: 10px; margin-top: 10px; width: 35px;height: 35px;\"> " +
                "                                    <div style=\"float: left; padding-left: 10px;\"> " +
                "                                        <h2 style=\"font-size: 20px;\">  Dear " +
                "                                            "+user_name+", an article assigned to you " +
                " " +
                "                                            <br> " +
                " " +
                "                                            <a href=\"http://dabacrm.ir/\" target=\"_blank\" " +
                "                                               style=\"color: #39b293; font-size: 16px; text-decoration: none !important\"> " +
                "                                                View in Dashboard " +
                "                                            </a> " +
                "                                        </h2> " +
                "                                    </div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task name --> " +
                "                                <div dir=\"rtl\"  charset=\"utf-8\" style=\"font-size: 20px; font-weight: bold; margin-top: 40px; color: #555;\"> "+title+"</div>" +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task description --> " +
                "                                <div dir=\"rtl\" style=\"font-size: 15px; font-weight: normal; color: #999;\"> " +description+
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <!-- Task name --> " +
                "                                <div dir=\"rtl\"  charset=\"utf-8\" style=\"font-size: 12px; font-weight: normal; margin-top: 40px; color: #555;\"> وضعیت حال حاضر:</div>" +
                "                            </td> " +
                "                        </tr> " +
                "                        <tr> " +
                "                            <td> " +
                "                                <div style=\"margin-top: 10px;\"> " +
                "                                    <!-- printed based on status --> " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("unknown") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #4c1b91;\">"+(status.equalsIgnoreCase("UNKNOWN") ? "UNKNOWN" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("REJECTED") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; width:0px!important visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #b42e2e;\">"+ (status.equalsIgnoreCase("REJECTED") ? "REJECTED" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("pending") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #555555; background-color: #fbe36d;\">"+(status.equalsIgnoreCase("pending") ? "PENDING" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("thinking") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #a6ab58;\">"+(status.equalsIgnoreCase("thinking") ? "THINKING" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("PUBLISH") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #1e811b;\">"+(status.equalsIgnoreCase("PUBLISH") ? "PUBLISH" : "")+"</div> " +
                " " +
                "                                    <div class=\"button\" style=\""+(status.equalsIgnoreCase("not_interested") ? "display: block; padding: 0 15px; " : "width: 0 !important; display: none; visibility: hidden")+" text-transform: lowercase !important;height: 30px;border-radius: 30px;line-height: 27px;font-weight: bold;float: left;margin: 0 10px 10px 0;color: #ffffff; background-color: #4d4d4d;\">"+(status.equalsIgnoreCase("NOT INTERESTED") ? "not_interested" : "")+"</div> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                         <tr><td>" +"<h3 style=\"margin-top: 10px\">  "+statusDesc+"</h3> " +
                "                           </td> </tr>  "+
                "                        <tr> " +
                "                            <td> " +
                "                                <h3 style=\"border-bottom: solid 1px #dddddd;margin-top: 40px\">Project:</h3> " +
                " " +
                "                                <div> " +
                "                                    <div style=\"width: 12px; height: 12px; border-radius: 3px; background-color: #be8108; float: left\"></div> " +
                " " +
                "                                    <span style=\"float: left; height: 12px; line-height: 12px; margin-left: 10px\">"+project+"</span> " +
                "                                </div> " +
                "                            </td> " +
                "                        </tr> " +
                "                    </table> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0;\">No. 24, 16w, kaj Sq, Sa'adat Abad, Tehran, Iran.</p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                " " +
                "            <tr> " +
                "                <td valign=\"middle\" class=\"hero bg_white\"> " +
                "                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\"> " +
                "                        <p style=\"margin: 0 0 1em 0\"> " +
                "                            &copy; <a href=\"http://dabacrm.ir/\" target=\"_blank\" style=\"color: #456543 !important; text-decoration: none !important;\">Daba CRM</a>. " +
                "                            All rights reserved. " +
                "                        </p> " +
                "                    </div> " +
                "                </td> " +
                "            </tr><!-- end tr --> " +
                "        </table> " +
                "    </div> " +
                "</center> " +
                "</body> " +
                "</html>";
    }


}