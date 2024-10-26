package rakxer.jbandcampscraper.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HtmlParserImpl {

    private final HttpClient client;

    public HtmlParserImpl() {
        this.client = HttpClient.newHttpClient();
    }

    public String getPage(String url) {
        String html = getHtml(url);
        Safelist safelist = Safelist.relaxed()
                .addTags("script")
                .addAttributes("script", "src", "data-tralbum")
                .addAttributes("link", "rel", "href")
                ;

        // Clean and format the HTML
        Document cleanDocument = Jsoup.parse(Jsoup.clean(html, safelist));
        cleanDocument.outputSettings().prettyPrint(true).indentAmount(4);

        return cleanDocument.toString();
    }

    private String getHtml(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Http error " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Couldn't get webpage", e);
        }
    }

}
