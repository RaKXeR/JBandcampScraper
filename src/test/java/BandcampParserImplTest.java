import rakxer.bandcamp.parser.BandcampParser;
import rakxer.bandcamp.parser.BandcampParserImpl;

public class BandcampParserImplTest extends BandcampParserTest {

    @Override
    protected BandcampParser getParser(String htmlFilePath) {
        return new BandcampParserImpl(getHtmlParser(htmlFilePath));
    }

}
