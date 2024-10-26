import rakxer.jbandcampscraper.parser.BandcampParser;
import rakxer.jbandcampscraper.parser.BandcampParserImpl;

public class BandcampParserImplTest extends BandcampParserTest {

    @Override
    protected BandcampParser getParser(String htmlFilePath) {
        return new BandcampParserImpl(getHtmlParser(htmlFilePath));
    }

}
