import rakxer.bandcamp.parser.BandcampParser;
import rakxer.bandcamp.parser.old.BandcampParserImpl;

@Deprecated
public class OldBandcampParserImplTest extends BandcampParserTest {

    @Override
    protected BandcampParser getParser(String htmlFilePath) {
        return new BandcampParserImpl(getHtmlParser(htmlFilePath));
    }

}
