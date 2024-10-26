import rakxer.jbandcampscraper.parser.BandcampParser;
import rakxer.jbandcampscraper.parser.old.BandcampParserImpl;

@Deprecated
public class OldBandcampParserImplTest extends BandcampParserTest {

    @Override
    protected BandcampParser getParser(String htmlFilePath) {
        return new BandcampParserImpl(getHtmlParser(htmlFilePath));
    }

}
