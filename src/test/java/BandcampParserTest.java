import org.junit.jupiter.api.Test;
import rakxer.bandcamp.parser.BandcampParser;
import rakxer.bandcamp.model.Song;
import rakxer.bandcamp.parser.HtmlParser;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class BandcampParserTest {

    private static final String TRACK_HTML = "src/test/resources/track.html";
    private static final String TRACK_URL = "https://rakxer.bandcamp.com/track/track-1";

    @Test
    void getSongs_ReturnsExpectedTitle_WhenPageIsValidTrack() {
        // Arrange
        String expected = "Cyber Latte LoFi - A Dirge for Fallen Autobots";
        BandcampParser parser = getParser(TRACK_HTML);
        // Act
        List<Song> songs = parser.getSongs(TRACK_URL);
        // Assert
        assertEquals(expected, songs.get(0).getTitle());
    }

    @Test
    void getSongs_ContainsStreamingURL_WhenPageIsValidTrack() {
        // Arrange
        Pattern pattern = Pattern.compile("bcbits\\.com/stream/[^/]*/mp3-\\d+/\\d+\\?.*token=.+");
        BandcampParser parser = getParser(TRACK_HTML);
        // Act
        List<Song> songs = parser.getSongs(TRACK_URL);
        // Assert
        boolean hasStreamingURL = pattern.matcher(songs.get(0).getStreamingURL()).find();
        assertTrue(hasStreamingURL);
    }

    @Test
    void getSongs_ContainsArtURL_WhenPageIsValidTrack() {
        // Arrange
        Pattern pattern = Pattern.compile("f4\\.bcbits\\.com/img/.+");
        BandcampParser parser = getParser(TRACK_HTML);
        // Act
        List<Song> songs = parser.getSongs(TRACK_URL);
        // Assert
        boolean hasArtURL = pattern.matcher(songs.get(0).getArtURL()).find();
        assertTrue(hasArtURL);
    }

    @Test
    void getSongs_ContainsDuration_WhenPageIsValidTrack() {
        // Arrange
        double expected = 327.958;
        BandcampParser parser = getParser(TRACK_HTML);
        // Act
        List<Song> songs = parser.getSongs(TRACK_URL);
        // Assert
        assertEquals(expected, songs.get(0).getDuration());
    }

    protected HtmlParser getHtmlParser(String htmlFilePath) {
        return new HtmlParserStub(htmlFilePath);
    }

    protected abstract BandcampParser getParser(String htmlFilePath);

}
