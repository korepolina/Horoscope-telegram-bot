package main.handlers;

import main.HoroscopeBot;
import main.ZodiacUtils;
import main.parser.Horoscope;
import main.parser.Compatibility;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final Map<Long, String> userZodiacs = new HashMap<>();
    private final Map<Long, Boolean> waitingForFirstCompatDate = new HashMap<>();
    private final Map<Long, Boolean> waitingForSecondCompatDate = new HashMap<>();
    private final Map<Long, String> tempFirstZodiac = new HashMap<>();

    private final HoroscopeBot bot;

    public MessageHandler(HoroscopeBot bot) {
        this.bot = bot;
    }

    public void handleMessage(long chatId, String text) {
        // первая дата для совместимости
        if (waitingForFirstCompatDate.getOrDefault(chatId, false)) {
            var maybeDate = ZodiacUtils.tryParseDate(text);
            if (maybeDate.isEmpty()) {
                bot.sender.sendMessageWithButtons(chatId, "❌ Неверная дата. Введите первую дату рождения в формате ДД.ММ:");
                return;
            }

            var date = maybeDate.get();
            String zodiac = ZodiacUtils.getZodiacByDate(date.getDayOfMonth(), date.getMonthValue());
            tempFirstZodiac.put(chatId, zodiac);

            waitingForFirstCompatDate.put(chatId, false);
            waitingForSecondCompatDate.put(chatId, true);

            bot.sender.sendMessageWithButtons(chatId, "Первая дата сохранена.\nВведите вторую дату рождения (ДД.ММ):");
            return;
        }

        // вторая дата для совместимости
        if (waitingForSecondCompatDate.getOrDefault(chatId, false)) {
            var maybeDate = ZodiacUtils.tryParseDate(text);
            if (maybeDate.isEmpty()) {
                bot.sender.sendMessageWithButtons(chatId, "❌ Неверная дата. Введите вторую дату рождения в формате ДД.ММ:");
                return;
            }

            var date = maybeDate.get();
            String zodiac2 = ZodiacUtils.getZodiacByDate(date.getDayOfMonth(), date.getMonthValue());
            String zodiac1 = tempFirstZodiac.get(chatId);

            waitingForSecondCompatDate.put(chatId, false);

            try {
                String result = Compatibility.parseCompatibility(zodiac1, zodiac2);
                bot.sender.sendMessageWithButtons(chatId, "❤️ Совместимость " + zodiac1 + " и " + zodiac2 + " в любви и браке:\n\n" + result);
            } catch (Exception e) {
                bot.sender.sendMessageWithButtons(chatId, "Произошла ошибка при получении данных совместимости.");
            }
            return;
        }

        // обычная дата рождения
        var maybeDate = ZodiacUtils.tryParseDate(text);
        if (maybeDate.isPresent()) {
            var date = maybeDate.get();
            String zodiac = ZodiacUtils.getZodiacByDate(date.getDayOfMonth(), date.getMonthValue());
            userZodiacs.put(chatId, zodiac);
            bot.sender.sendMessageWithButtons(chatId, "Твой знак зодиака: " + zodiac);
        } else {
            bot.sender.sendMessageWithButtons(chatId, "❌ Неверная дата. Проверь формат (ДД.ММ) и убедись, что такая дата существует.");
        }
    }

    public void sendHoroscope(long chatId) {
        if (!userZodiacs.containsKey(chatId)) {
            bot.sender.sendMessageWithButtons(chatId, "Сначала введи свою дату рождения через /start!");
            return;
        }

        String zodiac = userZodiacs.get(chatId);
        try {
            String text = Horoscope.parseByZodiac(zodiac);
            bot.sender.sendMessageWithButtons(chatId, "\uD83D\uDCAB Твой гороскоп на день \uD83D\uDCAB\n" + text);
        } catch (Exception e) {
            bot.sender.sendMessageWithButtons(chatId, "Произошла ошибка при получении гороскопа.");
        }
    }

    public void startCompatibility(long chatId) {
        waitingForFirstCompatDate.put(chatId, true);
        bot.sender.sendMessageWithButtons(chatId, "Введите первую дату рождения (ДД.ММ):");
    }
}
