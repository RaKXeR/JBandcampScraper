package rakxer.jbandcampscraper;

public class Song {
    private String title, streamingURL, artURL;
    
    public Song(String title, String streamingURL, String artURL) {
        this.title = title;
        this.streamingURL = streamingURL;
        this.artURL = artURL;
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
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setStreamingURL(String streamingURL) {
        this.streamingURL = streamingURL;
    }
    
    public void setArtworkURL(String artURL) {
        this.artURL = artURL;
    }
    
    @Override
    public String toString() {
        return String.format("{%s, %s, %s}", title, streamingURL, artURL);
    }
}