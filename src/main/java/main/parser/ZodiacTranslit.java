package main.parser;

import java.util.Map;

public class ZodiacTranslit {

    public static final Map<String, String> MAP = Map.ofEntries(
            Map.entry("овен", "oven"),
            Map.entry("телец", "telec"),
            Map.entry("близнецы", "bliznecy"),
            Map.entry("рак", "rak"),
            Map.entry("лев", "lev"),
            Map.entry("дева", "deva"),
            Map.entry("весы", "vesy"),
            Map.entry("скорпион", "skorpion"),
            Map.entry("стрелец", "strelec"),
            Map.entry("козерог", "kozerog"),
            Map.entry("водолей", "vodoley"),
            Map.entry("рыбы", "ryby")
    );
}
