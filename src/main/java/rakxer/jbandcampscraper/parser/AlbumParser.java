package rakxer.jbandcampscraper.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import rakxer.jbandcampscraper.dto.Album;

public class AlbumParser {

    private static final String SCRIPT_SRC = "https://s4.bcbits.com/bundle/bundle/1/tralbum_head";
    private static final String  ALBUM_ATTRIBUTE = "data-tralbum";

    public Album getAlbum(String html) {
        Document document = getDocument(html);
        Element script = getScript(document);
        if (script == null) {
            throw new RuntimeException("Unable to find Album data in HTML");
        }

        String albumJson = getAlbumJson(script);

        return parseAlbum(albumJson);
    }

    private Album parseAlbum(String albumJson) {
        Album album;
        try {
            ObjectMapper mapper = new ObjectMapper();
            album = mapper.readValue(albumJson, Album.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't parse Album JSON", e);
        }
        return album;
    }

    private String getAlbumJson(Element script) {
        return script.attr(ALBUM_ATTRIBUTE).replace("&quot;", "\"");
    }

    private Document getDocument(String html) {
        return Jsoup.parse(html, "", Parser.xmlParser());
    }

    private Element getScript(Document document) {
        return document.selectFirst(String.format("script[src^='%s']", SCRIPT_SRC));
    }

}
