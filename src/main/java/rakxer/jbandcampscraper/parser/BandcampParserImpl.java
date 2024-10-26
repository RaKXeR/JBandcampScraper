package rakxer.jbandcampscraper.parser;

import rakxer.jbandcampscraper.model.Song;
import rakxer.jbandcampscraper.dto.Album;
import rakxer.jbandcampscraper.mapper.JsonAlbumMapper;

import java.util.List;
import java.util.regex.Pattern;

public class BandcampParserImpl implements BandcampParser {

    private final Pattern URL_PATTERN = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/?$");
    private final HtmlParser htmlParser;

    public BandcampParserImpl(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    @Override
    public List<Song> getSongs(String url) {
        if (!isValidURL(url)) {
            throw new IllegalArgumentException("Invalid bandcamp URL");
        }
        String html = htmlParser.getPage(url);

        AlbumParser albumParser = new AlbumParser();
        Album album = albumParser.getAlbum(html);
        return JsonAlbumMapper.getSongs(album);
    }

    private boolean isValidURL(String url) {
        return url != null && URL_PATTERN.matcher(url).find();
    }

}
