package rakxer.bandcamp.model;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class SongBuilder {
    @Getter(AccessLevel.NONE)
    private String artId;
    private String artist, title, streamingURL, artURL;
    private Double duration;

    public SongBuilder artist(String artist) {
        this.artist = artist;
        return this;
    }

    public SongBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SongBuilder streamingURL(String streamingURL) {
        this.streamingURL = streamingURL;
        return this;
    }

    public SongBuilder artId(String artId) {
        this.artId = artId;
        return this;
    }

    public SongBuilder duration(double duration) {
        this.duration = duration;
        return this;
    }

    private String getFormattedTitle() {
        boolean hasArtistInTitle = title.contains(" - ");
        return hasArtistInTitle ? title : artist + " - " + title;
    }

    private String getArtUrl(String artId) {
        return String.format("https://f4.bcbits.com/img/a%s_16.jpg", artId);
    }

    public Song build() {
        if (title == null || streamingURL == null || duration == null) {
            throw new IllegalStateException("Title, streamingURL and duration must be set");
        }

        title = getFormattedTitle();
        artURL = getArtUrl(artId);
        return new Song(this);
    }

}
