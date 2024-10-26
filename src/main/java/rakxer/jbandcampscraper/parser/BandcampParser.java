package rakxer.jbandcampscraper.parser;

import rakxer.jbandcampscraper.Song;
import rakxer.jbandcampscraper.dto.Album;
import rakxer.jbandcampscraper.mapper.JsonAlbumMapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BandcampParser {

    private String html;

    public void setURL(String url) {
        if (!isValidURL(url)) {
            throw new IllegalArgumentException("Invalid bandcamp URL");
        }
        html = getPage(url);
    }

    public List<Song> getSongs() {
        if (html == null) {
            throw new IllegalStateException("Please use setURL() before calling getSongs()");
        }
        AlbumParser albumParser = new AlbumParser();
        Album album = albumParser.getAlbum(html);
        return JsonAlbumMapper.getSongs(album);
    }

    public static boolean isValidURL(String url) {
        Matcher matcher = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/?$").matcher(url);
        return matcher.find();
    }

    protected String getPage(String url) {
        HtmlParser parser = new HtmlParser();
        return parser.getPage(url);
    }

}
