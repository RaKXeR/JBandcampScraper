package rakxer.jbandcampscraper.parser;

import rakxer.jbandcampscraper.Song;

import java.util.List;

public interface BandcampParser {

    /**
     * Sets the URL to use for parsing.
     * @param url the URL of the song or album
     */
    void setURL(String url);

    /**
     * Returns a list of songs from the URL set by setURL.
     * @return a populated list of songs
     * @throws IllegalStateException if setURL has not been called before this method
     */
    List<Song> getSongs();

    /**
     * Returns the contents of the page at the given URL.
     * @param url the URL of the page to get
     * @return the contents of the page
     */
    String getPage(String url);

}
