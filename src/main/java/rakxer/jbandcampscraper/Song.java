package rakxer.jbandcampscraper;

public class Song {
    private String title, streamingURL, artURL;
    private double duration;

    public Song(String title, String streamingURL, String artURL, double duration) {
        this.title = title;
        this.streamingURL = streamingURL;
        this.artURL = artURL;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getStreamingURL() {
        return streamingURL;
    }

    public String getArtworkURL() {
        return artURL;
    }

    public double getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStreamingURL(String streamingURL) {
        this.streamingURL = streamingURL;
    }

    public void setArtworkURL(String artURL) {
        this.artURL = artURL;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s, %f}", title, streamingURL, artURL, duration);
    }

}
