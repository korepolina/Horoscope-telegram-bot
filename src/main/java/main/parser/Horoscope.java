package main.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Horoscope {

    public static String buildHoroscopeUrl(String zodiac) {
        return "https://my-calend.ru/goroskop/" + ZodiacTranslit.MAP.get(zodiac);
    }

    public static String parseByZodiac(String zodiac) throws Exception {

        Document doc = Jsoup.connect(buildHoroscopeUrl(zodiac)).get();

        return doc.select(".goroskop-text > p").text();
    }
}
