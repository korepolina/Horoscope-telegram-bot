package main.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Arrays;

public class Compatibility {

    private static final String[] zodiacOrder = {
            "овен", "телец", "близнецы", "рак", "лев", "дева",
            "весы", "скорпион", "стрелец", "козерог", "водолей", "рыбы"
    };

    public static String buildCompatibilityUrl(String z1, String z2) {
        int idx1 = Arrays.asList(zodiacOrder).indexOf(z1);
        int idx2 = Arrays.asList(zodiacOrder).indexOf(z2);

        String first, second;
        if (idx1 <= idx2) {
            first = z1;
            second = z2;
        } else {
            first = z2;
            second = z1;
        }

        return "https://my-calend.ru/zodiak-sovmestimost/" + ZodiacTranslit.MAP.get(first) + "-" + ZodiacTranslit.MAP.get(second);
    }


    public static String parseCompatibility(String z1, String z2) throws Exception {
        Document doc = Jsoup.connect(buildCompatibilityUrl(z1, z2)).get();
        String text = doc.select(".zodiak-sovmestimost-text > p").first().text();
        return text;
    }

}
