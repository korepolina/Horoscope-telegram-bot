package main;

import main.handlers.CommandHandler;
import main.handlers.MessageHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HoroscopeBot extends TelegramLongPollingBot {

    private final MessageHandler handler = new MessageHandler(this);
    public final CommandHandler sender = new CommandHandler(this);

    @Override
    public String getBotUsername() {
        return Config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return Config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText().trim().toLowerCase();

        switch (text) {
            case "/start":
                sender.sendMessageWithButtons(chatId, """
                        ✨ Добро пожаловать в Sign Speak! ✨
                        
                        Узнай, что звёзды приготовили для тебя сегодня!
                        Введите свой день рождения в формате ДД.ММ (например 06.12)
                        """);
                break;

            case "/help":
            case "помощь":
                sender.sendMessageWithButtons(chatId, """
                        📜 Доступные команды:
                        /start - приветствие
                        /horoday - гороскоп на день
                        /compatibility - совместимость
                        /help - список команд
                        """);
                break;

            case "/horoday":
            case "гороскоп на день":
                handler.sendHoroscope(chatId);
                break;

            case "/compatibility":
            case "совместимость":
                handler.startCompatibility(chatId);
                break;

            default:
                handler.handleMessage(chatId, text);
                break;
        }
    }
}
