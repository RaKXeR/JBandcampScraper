package rakxer.jbandcampscraper.parser;

import rakxer.jbandcampscraper.model.Song;

import java.util.List;

public interface BandcampParser {

    /**
     * Returns a list of songs parsed from the given URL.
     * @return a populated list of songs
     * @throws IllegalArgumentException if the URL is invalid
     */
    List<Song> getSongs(String url);

}
