package rakxer.jbandcampscraper.parser.old;

import lombok.Data;
import rakxer.jbandcampscraper.model.Song;
import rakxer.jbandcampscraper.parser.BandcampParser;
import rakxer.jbandcampscraper.parser.HtmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public class BandcampParserImpl implements BandcampParser {

    private String html;
    private final HtmlParser htmlParser;

    @Data
    private static class SongData {
        private String title;
        private String streamingURL;
        private double duration;
    }

    public BandcampParserImpl(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    @Override
    public List<Song> getSongs(String url) {
        if (!isValidURL(url)) {
            throw new IllegalArgumentException("Invalid bandcamp URL");
        }
        html = prune(htmlParser.getPage(url));

        List<SongData> songData = new ArrayList<>();
        String artId;

        String artist = getArtist();
        artId = getArtId();

        if (artist == null || artId == null) {
            throw new RuntimeException("Couldn't find all the necessary variables on the webpage provided (bandcamp)." + artist + artId);
        }

        List<String> titles = getSongTitles(artist);

        for (String title : titles) {
            SongData song = new SongData();
            song.setTitle(title);
            songData.add(song);
        }

        fillStreamingURLs(songData);
        fillSongDurations(songData);

        List<Song> songs = new ArrayList<>();
        for (SongData song : songData) {
            songs.add(new Song.Builder()
                    .artist(artist)
                    .title(song.getTitle())
                    .streamingURL(song.getStreamingURL())
                    .artId(artId)
                    .duration(song.getDuration())
                    .build()
            );
        }
        return songs;
    }

    private void fillSongDurations(List<SongData> songs) {
        Matcher matcher = Pattern.compile("duration\":([^,]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setDuration(Double.parseDouble(matcher.group(1)));
        }
    }

    private void fillStreamingURLs(List<SongData> songs) {
        Matcher matcher = Pattern.compile("128\":\"[^/]*/*([^\"]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setStreamingURL(matcher.group(1));
        }
    }

    private List<String> getSongTitles(String artist) {
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

    private String getArtId() {
        String artId;
        Matcher matcher = Pattern.compile("\"art_id\":\\s*([^,]*)").matcher(html);
        // Not the correct art URL, but values are the same, so it should be fine
        artId = matcher.find() ? matcher.group(1) : null;
        return artId;
    }

    private String getArtist() {
        String artist;
        Matcher matcher = Pattern.compile("artist\":\\s*\"([^\"]*)").matcher(html);
        // Not the correct artist variable, but values are the same, so it should be fine
        artist = matcher.find() ? matcher.group(1) : null;
        return artist;
    }

    private boolean isValidURL(String url) {
        Matcher matcher = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/?$").matcher(url);
        return matcher.find();
    }

    private String prune(String html) {
        // Reduces the size of the html string by 90+% to speed up future regex
        html = html.split("data-tralbum=", 2)[1].split("data-cart=", 2)[0];
        html = html.replace("&quot;", "\"");
        return html;
    }

}
