package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Map;

public class Parser {

    private static final Map<String, String> zodiacUrls = Map.ofEntries(
            Map.entry("овен", "https://my-calend.ru/goroskop/oven"),
            Map.entry("телец", "https://my-calend.ru/goroskop/telec"),
            Map.entry("близнецы", "https://my-calend.ru/goroskop/bliznecy"),
            Map.entry("рак", "https://my-calend.ru/goroskop/rak"),
            Map.entry("лев", "https://my-calend.ru/goroskop/lev"),
            Map.entry("дева", "https://my-calend.ru/goroskop/deva"),
            Map.entry("весы", "https://my-calend.ru/goroskop/vesy"),
            Map.entry("скорпион", "https://my-calend.ru/goroskop/skorpion"),
            Map.entry("стрелец", "https://my-calend.ru/goroskop/strelec"),
            Map.entry("козерог", "https://my-calend.ru/goroskop/kozerog"),
            Map.entry("водолей", "https://my-calend.ru/goroskop/vodoley"),
            Map.entry("рыбы", "https://my-calend.ru/goroskop/ryby")
    );

    public static String parseByZodiac(String zodiac) throws Exception {
        String url = zodiacUrls.get(zodiac);

        Document doc = Jsoup.connect(url).get();

        return doc.select(".goroskop-text > p").text();
    }
}
