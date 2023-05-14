package project.bot.javaBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    //вернет имя бота
    @Override
    public String getBotUsername() {
        return "http://t.me/my_joker_for_my_family_bot";
    }

    //уникальный пароль который генерирует телега для нашего бота
    @Override
    public String getBotToken() {
        return "5851801344:AAHdKM3guDdHaaQm8f0ZBNxBYclFUxajY-I";
    }

    //запросы от пользователей
    @Override
    public void onUpdateReceived(Update update) {
        //узнаем что написал клиент
        Message message = update.getMessage();
        System.out.println("message received :" + message.getText());
//отправляем смс клиенту
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Hello мой Сын, i received your message:" + message.getText());
//теперь указываем какому именно клиенту надо отправить смс
        sendMessage.setChatId(String.valueOf(message.getChatId()));

//ставим условие
        if (message.getText().equals("/start")){
            String text = "welcome to joker bot! Please pass the meal of the day\n";

//отправляем кнопки в ЮА телеграмму
            sendMessage.enableMarkdown(true);

                ReplyKeyboardMarkup menuKeyboard = getMenuKeyboard();//получаем кнопки
                 sendMessage.setReplyMarkup(menuKeyboard);//назначаем кнопки

            sendMessage.setText(text);
        }


        if (message.getText().equals("breakfast")) {
            String menu = "menu breakfast\n" +
                    "1: омлет\n" +
                    "2: бекон\n" +
                    "3: сок\n";
            sendMessage.setText(menu);
        }
        if (message.getText().equals("dinner")) {
            String menu = "menu dinner\n" +
                    "1: макароны\n" +
                    "2: борщ\n" +
                    "3: картошка\n";
            sendMessage.setText(menu);
        }
        if (message.getText().equals("lunch")){
            String text = "Lunch is in processing";
            sendMessage.setText(text);
        }
        if (message.getText().equals("supper")) {
            String text = "Supper is in processing";
            sendMessage.setText(text);
        }

//отправляем смс
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }


    //делаем кнопки к телеграм боту
private ReplyKeyboardMarkup getMenuKeyboard(){
    ReplyKeyboardMarkup rem = new ReplyKeyboardMarkup();//объект, представляет собой набор из кнопок в телеграмме
     rem.setSelective(true);
      rem.setResizeKeyboard(true);
       rem.setOneTimeKeyboard(false);

//создаем список кнопок
    List<KeyboardRow> list = new ArrayList<>();
     KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add("breakfast");
            keyboardRow.add("dinner");

     KeyboardRow keyboardRowSecond = new KeyboardRow();
            keyboardRowSecond.add("lunch");
            keyboardRowSecond.add("supper");

     list.add(keyboardRow);
     list.add(keyboardRowSecond);

     rem.setKeyboard(list);

     return rem;
}


}

