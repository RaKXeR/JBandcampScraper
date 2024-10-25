package rakxer.jbandcampscraper;

import rakxer.jbandcampscraper.BandcampParser.Song;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BandcampInfo {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert a bandcamp URL: ");
        String url = s.next().trim();
        Matcher matcher = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/{0,1}$").matcher(url);
        if (matcher.find()) {
            try {
                List<Song> songs = BandcampParser.getSongs(url);
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
}