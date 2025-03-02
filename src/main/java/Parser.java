import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class Parser {

    private static Document getPage() throws IOException {
        String url = "https://pogoda.spb.ru";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getPage());
    }
}
