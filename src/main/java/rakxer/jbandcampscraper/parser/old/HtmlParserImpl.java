package rakxer.jbandcampscraper.parser.old;

import rakxer.jbandcampscraper.parser.HtmlParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Deprecated
public class HtmlParserImpl implements HtmlParser {

    private final HttpClient client;

    public HtmlParserImpl() {
        this.client = HttpClient.newHttpClient();
    }

    public String getPage(String url) {
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
