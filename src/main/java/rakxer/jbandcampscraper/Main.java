package rakxer.jbandcampscraper;

import rakxer.jbandcampscraper.parser.BandcampParser;
import rakxer.jbandcampscraper.parser.BandcampParserImpl;
import rakxer.jbandcampscraper.parser.HtmlParserImpl;

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
        BandcampParser parser = new BandcampParserImpl(new HtmlParserImpl());
        List<Song> songs = parser.getSongs(url);
        return songs.stream().map(Song::toString).collect(Collectors.toList());
    }

}
