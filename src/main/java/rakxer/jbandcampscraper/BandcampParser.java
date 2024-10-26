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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BandcampParser {

    private final List<Song> songs = new ArrayList<>();
    private String artist;

    public List<Song> getSongs(String url) {
        String html = getPage(url);
        String artURL;

        html = prune(html);

        artist = getArtist(html);
        artURL = getArtURL(html);

        if (artist == null || artURL == null) {
            throw new RuntimeException("Couldn't find all the necessary variables on the webpage provided (bandcamp)." + artist + artURL);
        }

        List<String> titles = getSongTitles(html);

        for (String title : titles) {
            songs.add(new Song(title, null, artURL, 0));
        }

        fillStreamingURLs(html);
        fillSongDurations(html);

        return songs;
    }

    private void fillSongDurations(String html) {
        Matcher matcher = Pattern.compile("duration\":([^,]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setDuration(Double.parseDouble(matcher.group(1)));
        }
    }

    private void fillStreamingURLs(String html) {
        Matcher matcher = Pattern.compile("128\":\"[^/]*/*([^\"]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setStreamingURL(matcher.group(1));
        }
    }

    private List<String> getSongTitles(String html) {
        List<String> titles = new ArrayList<>();
        Matcher matcher = Pattern.compile("title\":\"([^\"]*)").matcher(html);

        for (int find_count = 0; matcher.find(); find_count++) {
            String title;
            // Check if the track name already includes the artist's name. If it doesn't, add it.
            if (matcher.group(1).contains(" - ")) {
                title = matcher.group(1);
            } else {
                title = artist + " - " + matcher.group(1);
            }
            titles.add(title);

            if (find_count + 1 == matcher.groupCount()) {
                break;
            }
        }

        return titles;
    }

    private String getArtURL(String html) {
        String artURL;
        Matcher matcher = Pattern.compile("\"art_id\":\\s*([^,]*)").matcher(html);
        // Not the correct art URL, but values are the same, so it should be fine
        artURL = matcher.find() ? "https://f4.bcbits.com/img/a" + matcher.group(1) + "_16.jpg" : null;
        return artURL;
    }

    private String getArtist(String html) {
        String artist;
        Matcher matcher = Pattern.compile("artist\":\\s*\"([^\"]*)").matcher(html);
        // Not the correct artist variable, but values are the same, so it should be fine
        artist = matcher.find() ? matcher.group(1) : null;
        return artist;
    }

    private String prune(String html) {
        // Reduces the size of the html string by 90+% to speed up future regex
        html = html.split("data-tralbum=", 2)[1].split("data-cart=", 2)[0];
        html = html.replace("&quot;", "\"");
        return html;
    }

    private String getPage(String url) {
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
            throw new RuntimeException("Couldn't get webpage", e);
        }

        return html;
    }

}
