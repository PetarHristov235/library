package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String receiver,
                          String receiverName,
                          String title,
                          String address,
                          String phoneNumber,
                          String date,
                          String orderNumber){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("blogemailservice512@gmail.com");
        message.setTo(receiver);
        message.setText("Скъпи "+receiverName+",\n" +
                "\n" +
                "Радваме се да ви информираме, че вашата поръчка от нашата библиотека беше успешно обработена и потвърдена.\n" +
                "\n" +
                "Детайлите на вашата поръчка:\n" +
                "\n" +
                "Номер на поръчка : "+orderNumber+"\n" +
                "Дата на поръчка : "+date+"\n" +
                "Адрес за доставка : "+address+"\n" +
                "Телефонен номер : "+phoneNumber+"\n"+
                "Поръчана книга : \""+title+"\"\n" +
                "\n" +
                "Моля, прегледайте горепосочените детайли и ни уведомете, ако имате въпроси или притеснения. Ако трябва да направите някакви промени в поръчката си, моля, свържете се с нас възможно най-скоро.\n" +
                "\n" +
                "Благодарим ви още веднъж, че избрахте нашата библиотека, за да изберете вашата книга. Приятно четене и очакваме с нетърпение следващата ни среща!\n" );
        message.setSubject("Михбук потвърждаване на поръчка - "+orderNumber);


        mailSender.send(message);
    }

}
