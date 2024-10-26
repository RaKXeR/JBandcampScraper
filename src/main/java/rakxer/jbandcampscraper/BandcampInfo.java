package rakxer.jbandcampscraper;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class BandcampInfo {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert a bandcamp URL: ");
        String url = s.next().trim();

        if (!isValidBandcampURL(url)) {
            System.out.println("The URL you inserted wasn't a valid bandcamp link.");
            throw new IllegalArgumentException("Invalid URL");
        }

        List<String> songs = getSongs(url);
        songs.forEach(System.out::println);
    }

    private static boolean isValidBandcampURL(String url) {
        Matcher matcher = Pattern.compile("\\w*\\.bandcamp.com/(track|album)/[^/]*/?$").matcher(url);
        return matcher.find();
    }

    private static List<String> getSongs(String url) {
        List<Song> songs = new BandcampParser().getSongs(url);
        return songs.stream().map(Song::toString).collect(Collectors.toList());
    }

}