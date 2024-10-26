package rakxer.bandcamp.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Song {
    private final String title, streamingURL, artURL;
    private final double duration;

    private Song(Builder builder) {
        this.title = builder.title;
        this.streamingURL = builder.streamingURL;
        this.artURL = builder.artURL;
        this.duration = builder.duration;
    }

    public static class Builder {
        private String artist, title, streamingURL, artId, artURL;
        private Double duration;

        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder streamingURL(String streamingURL) {
            this.streamingURL = streamingURL;
            return this;
        }

        public Builder artId(String artId) {
            this.artId = artId;
            return this;
        }

        public Builder duration(double duration) {
            this.duration = duration;
            return this;
        }

        private String getFormattedTitle() {
            boolean hasArtistInTitle = title.contains(" - ");
            return hasArtistInTitle ? title : artist + " - " + title;
        }

        private String getArtURL() {
            return String.format("https://f4.bcbits.com/img/a%s_16.jpg", artId);
        }

        public Song build() {
            if (title == null || streamingURL == null || duration == null) {
                throw new IllegalStateException("Title, streamingURL and duration must be set");
            }

            title = getFormattedTitle();
            artURL = getArtURL();
            return new Song(this);
        }

    }

}
