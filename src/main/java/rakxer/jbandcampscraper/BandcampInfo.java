package rakxer.jbandcampscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;


public class BandcampInfo {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert a bandcamp URL: ");
        String url = s.next().trim();
        Matcher matcher = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/{0,1}$").matcher(url);
        if (matcher.find()) {
            try {
                List<Song> songs = getSongs(url);
                songs.stream().forEach(song -> {
                    System.out.println(song.toString());
                });
            } catch (IOException ex) {
                Logger.getLogger(BandcampInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("The URL you inserted wasn't a valid bandcamp link.");
        }
    }
    
    private static ArrayList<Song> getSongs(String url) throws IOException {
        String artist, artURL;
        ArrayList<Song> songs = new ArrayList<>();
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        int errcode = client.executeMethod(get);
        if (errcode != 200) {
            throw new RuntimeException("Http error " + errcode + " from bandcamp");
        }
        
        String html = IOUtils.toString(get.getResponseBodyAsStream(), "utf-8");
        html = html.split("var SiteData", 2)[1].split("CurrencyData", 2)[0]; //reduces the size of the html string by 90+% to speed up future regex
        
        Matcher matcher = Pattern.compile("artist:\\s\"([^\"]*)").matcher(html);
        artist = matcher.find() ? matcher.group(1) : null; //this artist variable is not the correct artist variable, but values are the same, so it should be fine
        matcher = Pattern.compile("art_id\"*:\\s([^,]*)").matcher(html);
        artURL = matcher.find() ? "https://f4.bcbits.com/img/a" + matcher.group(1) + "_16.jpg" : null; //same deal as with the artist variable
        
        if (artist == null || artURL == null) {
            throw new RuntimeException("Couldn't find all the necessary variables on the webpage provided (bandcamp)." + artist + artURL);
        }
        matcher = Pattern.compile("title\":\"([^\"]*)").matcher(html);
        //must discard the first result because that's the "current" track, which doesn't have all info and the info that it does have is duped
        matcher.find();
        while (matcher.find()) {
            String title;
            //tries to check if the track name already includes the artist's name. if it doesn't, it adds it.
            if (matcher.group(1).contains(" - ")) {
                title = matcher.group(1);
            } else {
                title = artist + " - " + matcher.group(1);
            }
            songs.add(new Song(title, null, artURL));
        }
        matcher = Pattern.compile("128\":\"//([^\"]*)").matcher(html);
        for (int i = 0; i < songs.size() && matcher.find(); i++) {
            songs.get(i).setStreamingURL(matcher.group(1));
        }
        return songs;
    }
}