package rakxer.bandcamp.util;

import rakxer.bandcamp.model.Song;
import rakxer.bandcamp.parser.BandcampParser;
import rakxer.bandcamp.parser.HtmlParserImpl;
import rakxer.bandcamp.parser.old.BandcampParserImpl;

import java.util.List;
import java.util.stream.Collectors;

public class BandcampFetcher {

    private final BandcampParser parser;

    public BandcampFetcher() {
        this.parser = new BandcampParserImpl(new HtmlParserImpl());
    }

    public List<Song> getSongs(String url) {
        return parser.getSongs(url);
    }

    public List<String> asStrings(List<Song> songs) {
        return songs.stream().map(Song::toString).collect(Collectors.toList());
    }

    public String asString(List<Song> songs) {
        return songs.stream().map(Song::toString).collect(Collectors.joining("\n"));
    }

}
