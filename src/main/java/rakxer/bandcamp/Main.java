package rakxer.bandcamp;

import rakxer.bandcamp.model.Song;
import rakxer.bandcamp.parser.BandcampParser;
import rakxer.bandcamp.parser.BandcampParserImpl;
import rakxer.bandcamp.parser.HtmlParserImpl;
import rakxer.bandcamp.util.BandcampFetcher;

import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insert a bandcamp URL: ");
        String url = s.next().trim();

        List<String> songs = getSongs(url);
        songs.forEach(System.out::println);
    }

    private static List<String> getSongs(String url) {
        BandcampFetcher fetcher = new BandcampFetcher();
        List<Song> songs = fetcher.getSongs(url);
        return fetcher.asStrings(songs);
    }

}
