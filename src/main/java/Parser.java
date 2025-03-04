import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static int WAITING_TIME = 3000;
    public static String URL_ADRESS = "https://pogoda.spb.ru";

    public final static String MORNING = "Утро";
    public final static String DAY = "День";
    public final static String EVENING = "Вечер";
    public final static String NIGHT = "Ночь";


    public static void main(String[] args) throws Exception {
        Document page = getPage();
        // css query language
        Element tableWeather = page.select("table[class=wt]").first();
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");
        int index = 0;

        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);
            System.out.println(date + "                   Явления         Темп.  Давление Влажность   Ветер");
           int iterationCount = printPartValues(values, index);
           index = index + iterationCount;
        }

    }

    private static int printPartValues(Elements values, int index) {  
        int iterationCount = 4;
        if (index == 0) {
            Element valueLn = values.get(3);
            String partOfDay = valueLn.text();

                    switch (partOfDay) {
                        case "Утро":
                            iterationCount = 3;
                            break;
                        case "День":
                            iterationCount = 2;
                        case "Вечер":
                        iterationCount = 1;
                        case "Ночь":
                            iterationCount = 0;
                        default:
                            break;
                    }

           /* boolean isMorning = valueLn.text().contains("Утро");
            if (isMorning) {
                iterationCount = 3;
            }*/
            for(int i = 0; i < iterationCount; i++) {
                Element valueLine = values.get(index + i);
                 for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "    ");
            } 
                System.out.println();
        }
        } else {
        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "    ");
            } 
            System.out.println();
        }
    } 
    return iterationCount;
    }


    /*public static void getWeatherTable() {
            System.out.println("     Явления     Темп.     Давление     Влажность      Ветер");
    }*/

    private static Document getPage() throws IOException {
        Document page = Jsoup.parse(new URL(URL_ADRESS), WAITING_TIME);
        return page;
    }

    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string");
    }

}
