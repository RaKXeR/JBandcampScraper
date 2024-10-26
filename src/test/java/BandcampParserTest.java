import org.junit.jupiter.api.Test;
import rakxer.jbandcampscraper.BandcampParser;
import rakxer.jbandcampscraper.Song;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BandcampParserTest {

    private static final String TRACK_HTML = "src/test/resources/track.html";

    @Test
    void getSongs_ReturnsExpectedTitle_WhenPageIsValidTrack() {
        String expected = "Cyber Latte LoFi - A Dirge for Fallen Autobots";
        BandcampParser parser = new BandcampParserStub(TRACK_HTML);
        parser.setURL("https://rakxer.bandcamp.com/track/track-1");

        List<Song> songs = parser.getSongs();

        assertEquals(expected, songs.get(0).getTitle());
    }

    @Test
    void getSongs_ContainsStreamingURL_WhenPageIsValidTrack() {
        Pattern pattern = Pattern.compile("bcbits\\.com/stream/[^/]*/mp3-\\d+/\\d+\\?.*token=.+");
        BandcampParser parser = new BandcampParserStub(TRACK_HTML);
        parser.setURL("https://rakxer.bandcamp.com/track/track-1");

        List<Song> songs = parser.getSongs();

        boolean hasStreamingURL = pattern.matcher(songs.get(0).getStreamingURL()).find();
        assertTrue(hasStreamingURL);
    }

    @Test
    void getSongs_ContainsArtURL_WhenPageIsValidTrack() {
        Pattern pattern = Pattern.compile("f4\\.bcbits\\.com/img/.+");
        BandcampParser parser = new BandcampParserStub(TRACK_HTML);
        parser.setURL("https://rakxer.bandcamp.com/track/track-1");

        List<Song> songs = parser.getSongs();

        boolean hasArtURL = pattern.matcher(songs.get(0).getArtURL()).find();
        assertTrue(hasArtURL);
    }

    @Test
    void getSongs_ContainsDuration_WhenPageIsValidTrack() {
        double expected = 327.958;
        BandcampParser parser = new BandcampParserStub(TRACK_HTML);
        parser.setURL("https://rakxer.bandcamp.com/track/track-1");

        List<Song> songs = parser.getSongs();

        assertEquals(expected, songs.get(0).getDuration());
    }

}
