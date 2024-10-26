package rakxer.bandcamp.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Song {
    private final String title, streamingURL, artURL;
    private final double duration;

    public Song(SongBuilder builder) {
        this.title = builder.getTitle();
        this.streamingURL = builder.getStreamingURL();
        this.artURL = builder.getArtURL();
        this.duration = builder.getDuration();
    }

}
