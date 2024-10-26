package rakxer.jbandcampscraper;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BandcampParser {

    public ArrayList<Song> getSongs(String url) throws IOException {
        String html = getPageHtml(url);
        ArrayList<Song> songs = new ArrayList<>();
        String artist, artURL;

        html = html.split("data-tralbum=", 2)[1].split("data-cart=", 2)[0]; //reduces the size of the html string by 90+% to speed up future regex
        html = html.replace("&quot;", "\"");

        Matcher matcher = Pattern.compile("artist\":\\s*\"([^\"]*)").matcher(html);
        artist = matcher.find() ? matcher.group(1) : null; //this artist variable is not the correct artist variable, but values are the same, so it should be fine
        System.out.println(artist);
        matcher = Pattern.compile("\"art_id\":\\s*([^,]*)").matcher(html);
        artURL = matcher.find() ? "https://f4.bcbits.com/img/a" + matcher.group(1) + "_16.jpg" : null; //same deal as with the artist variable
        System.out.println(artURL);

        if (artist == null || artURL == null) {
            throw new RuntimeException("Couldn't find all the necessary variables on the webpage provided (bandcamp)." + artist + artURL);
        }
        matcher = Pattern.compile("title\":\"([^\"]*)").matcher(html);
        //discard last result
        int find_count = 0;
        while (matcher.find()) {
            String title;
            //tries to check if the track name already includes the artist's name. if it doesn't, it adds it.
            if (matcher.group(1).contains(" - ")) {
                title = matcher.group(1);
            } else {
                title = artist + " - " + matcher.group(1);
            }
            songs.add(new Song(title, null, artURL, 0));
            find_count++;
            if (find_count == matcher.groupCount()) {
                break;
            }
        }
        matcher = Pattern.compile("128\":\"[^/]*/*([^\"]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setStreamingURL(matcher.group(1));
        }
        matcher = Pattern.compile("duration\":([^,]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setDuration(Double.parseDouble(matcher.group(1)));
        }
        return songs;
    }

    private String getPageHtml(String url) {
        String html;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        try (CloseableHttpResponse response = client.execute(get)) {
            int errcode = response.getCode();
            if (errcode != 200) {
                throw new RuntimeException("Http error " + errcode + " from bandcamp");
            }

            html = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Couldn't get the webpage from Bandcamp");
        }

        return html;
    }

}
