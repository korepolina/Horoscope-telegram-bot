package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.commands.HoroscopeDayCommand;

import java.util.HashMap;
import java.util.Map;

public class HoroscopeBot extends TelegramLongPollingBot {

    private final Map<Long, String> userZodiacs = new HashMap<>();

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
        String text = update.getMessage().getText().trim();

        switch (text.toLowerCase()) {
            case "/start":
                sendText(chatId, """
                        ✨ Добро пожаловать в Sign Speak! ✨
                        
                        Узнай, что звёзды приготовили для вас сегодня!
                        
                        Введите свой день рождения в формате ДД.ММ (например 06.12).
                        Чтобы узнать список команд — /help
                        """);
                break;

            case "/help":
                sendText(chatId, """
                        📜 Доступные команды:
                        /start - приветствие
                        /horoday - гороскоп на день
                        /help - список команд
                        """);
                break;

            case "/horoday":
                if (userZodiacs.containsKey(chatId)) {
                    String zodiac = userZodiacs.get(chatId);
                    sendText(chatId, "Твой гороскоп на день: " + HoroscopeDayCommand.getHoroscopeForSign(zodiac));
                } else {
                    sendText(chatId, "Сначала введи свою дату рождения через /start!");
                }
                break;

            default:
                var maybeDate = ZodiacUtils.tryParseDate(text);
                if (maybeDate.isPresent()) {
                    var date = maybeDate.get();
                    String zodiac = ZodiacUtils.getZodiacByDate(date.getDayOfMonth(), date.getMonthValue());
                    userZodiacs.put(chatId, zodiac);
                    sendText(chatId, "Твой знак зодиака: " + zodiac);
                } else {
                    sendText(chatId, "❌ Неверная дата. Проверь формат (ДД.ММ) и убедись, что такая дата существует.");
                }
                break;

        }
    }

    private void sendText(long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
