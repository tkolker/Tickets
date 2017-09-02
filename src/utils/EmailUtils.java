package utils;

import logic.ShowArchive;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

    public static String builtEmailBodyForNewMessage(String sellerName, String buyerName)
    {
        StringBuilder text = new StringBuilder("היי ").append(sellerName).append(",").append("\n\n\n").
                append("התקבלה הודעה חדשה מאת ").append(buyerName).append(".").append("\n\n\n").
                append("★ צוות מכרטסים");
        return text.toString();
    }

    public static String builtEmailBodyForBuyer(String sellerEmail, String sellerName, ShowArchive show, String buyerName)
    {
        int totalAmount = show.getShowPrice() * show.getShowTickets();
        StringBuilder text = new StringBuilder("היי ").append(buyerName).append(",").
                append("\n\n\n").append("קנית ").append(show.getShowTickets()).append(" כרטיסים להופעה ").append(show.getShowName()).append(".")
                .append("\n\n").append("סך הכל שולם ").append(totalAmount).append(" שקלים חדשים.").append("\n\n").append("מספר הזמנה: ").append(show.getKey()).append(".\n\n")
                .append("ליצירת קשר עם המוכר: ").append("\n").append(sellerName).append(" ").append(sellerEmail).append("\n\n\n")
                .append("★ צוות מכרטסים");
        return text.toString();
    }

    public static String builtEmailBodyForSeller(String sellerName, ShowArchive show, String buyerEmail, String buyerName)
    {
        int totalAmount = show.getShowPrice() * show.getShowTickets();
        StringBuilder text = new StringBuilder("היי ").append(sellerName).append(",").
                append("\n\n").append("נרכשו ").append(show.getShowTickets()).append(" כרטיסים להופעה ").append(show.getShowName()).append(" שפירסמת למכירה.")
                .append("\n\n").append("סך הכל הועבר לחשבונך ").append(totalAmount).append(" שקלים חדשים.").append("\n\n").append("ליצירת קשר עם הקונה: ").append("\n").append(buyerName).append(" ").append(buyerEmail).append("\n\n\n")
                .append("★ צוות מכרטסים");
        return text.toString();
    }

    public static boolean sendEmailToSeller(String sellerEmail, String sellerName, ShowArchive show, String buyerEmail, String buyerName) {
        String body = builtEmailBodyForSeller(sellerName, show, buyerEmail, buyerName);
        String subject = "מכרת כרטיס במכרטסים!";
        sendEmail(sellerEmail, subject, body);

        return true;
    }

    public static boolean sendEmailToBuyer(String sellerEmail, String sellerName, ShowArchive show, String buyerEmail, String buyerName) {
        String body = builtEmailBodyForBuyer(sellerEmail, sellerName, show, buyerName);
        String subject = "קנית כרטיס במכרטסים!";
        sendEmail(buyerEmail, subject, body);

        return true;
    }

    public static boolean sendEmailOfNewMessage(String sellerEmail, String sellerName, String buyerName) {
        String body = builtEmailBodyForNewMessage(sellerName, buyerName);
        String subject = "התקבלה הודעה חדשה!";
        sendEmail(sellerEmail, body, subject);

        return true;
    }

    private static void sendEmail(String mail, String subject, String body)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mecartesim@gmail.com", "Tickets2017");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mecartesim@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
