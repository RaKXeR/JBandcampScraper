package rakxer.jbandcampscraper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert a bandcamp URL: ");
        String url = s.next().trim();

        List<String> songs = getSongs(url);
        songs.forEach(System.out::println);
    }

    private static List<String> getSongs(String url) {
        BandcampParser parser = new BandcampParser();
        parser.setURL(url);
        List<Song> songs = parser.getSongs();
        return songs.stream().map(Song::toString).collect(Collectors.toList());
    }

}
