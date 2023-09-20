package com.DXvalley.chatbot.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String fullName, String reason, String userName, String password) {
//        SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            if (reason == "PASSWORD_CHANGED") {
                helper.setText(emailBuilderForUserConfirmation("Eyasu", "http://www.youtube.com", fullName), true);
            } else if (reason == "USER_CREATED") {
                helper.setText(emailBuilderForCreateUser(fullName, password, userName, to), true);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("error: " + e);
            throw new RuntimeException(e);
        }
    }

    public String emailBuilderForCreateUser(String fullName, String password, String userName, String email) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\"> \n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0 \"></span> \n" +
                " <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "<tbody>\n" +
                " <tr>\n" +
                "<td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                " <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "<tbody>\n" +
                " <tr>\n" +
                " <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                " <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "<tbody>\n" +
                "  <tr>\n" +
                "   <td style=\"padding-left:10px\"></td>\n" +
                "<td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                " <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Account Created!</span>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                " </table>\n" +
                " </td>\n" +
                "</tr>\n" +
                " </tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                " </tbody>\n" +
                "</table>\n" +
                "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                " <td>\n" +
                " <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "  <tbody>\n" +
                " <tr>\n" +
                " <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                " </tr>\n" +
                " </tbody>\n" +
                "  </table>\n" +
                " </td>\n" +
                " <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                " </tr>\n" +
                "</tbody>\n" +
                " </table>\n" +
                " <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "<tbody>\n" +
                " <tr>\n" +
                " <td height=\"30\"><br></td>\n" +
                "</tr>\n" +
                " <tr>\n" +
                "<td width=\"10\" valign=\"middle\"><br></td>\n" +
                " <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                " <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Dear " + fullName + ",</p>\n"
                +
                "\n" +
                " <p>\n" +
                "   We are pleased to inform you that your account has been created in our system by our Super Admin. You can now access the system using the login credential provided below to you.\n" +
                " To access the system, please go to\n" +
                " <a href=\"www.equbadmin.com\" style=\"text-decoration: none;\">Visit Oromia admin</a> \n" +
                "and enter your login credentials. \n" +
                " <p><b style=\"color:#c74e22\">We kindly request you to change your password after you login with default credential!</b>\n" +
                "   If you have any questions or concerns, please do not hesitate to contact us. We are always here to help.</p>\n" +
                "</p>\n" +
                " <p>username: <span style=\"color: rgb(208, 113, 12)\">\n"
                +
                userName
                +
                "</span> or <span style=\"color: rgb(208, 113, 12);\">\n" +
                email +
                "</span></p>\n" +
                " <p>password:  <span style=\"color: rgb(208, 113, 12);\">\n"
                +
                password
                +
                "</span>" +
                "<p> Thank you!</p>\n" +
                " </td>\n" +
                " </tbody>\n" +
                " </table>\n" +
                "\n " +
                "</div>";
    }

    public String emailBuilderForUserConfirmation(String name, String link, String fullName) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
                +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Password changed!</span>\n"
                +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
                + fullName
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> You have successfully changed your password! </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Thank you!</blockquote>\n"
                +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}