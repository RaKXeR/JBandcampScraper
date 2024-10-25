package rakxer.jbandcampscraper;

import lombok.Data;

@Data
public class Song {
    private String title, streamingURL, artURL;
    private double duration;

    public Song(String title, String streamingURL, String artURL, double duration) {
        this.title = title;
        this.streamingURL = streamingURL;
        this.artURL = artURL;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s, %f}", title, streamingURL, artURL, duration);
    }

}
