import rakxer.jbandcampscraper.parser.BandcampParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

public class BandcampParserStub extends BandcampParserImpl {

    private final String FILE_PATH;

    public BandcampParserStub(String filePath) {
        super();
        FILE_PATH = filePath;
    }

    @Override
    protected String getPage(String ignored) {
        try {
            return Files.readString(Path.of(FILE_PATH));
        } catch (IOException e) {
            fail("Couldn't read the file: " + FILE_PATH);
            throw new RuntimeException("Unreachable code");
        }
    }

}
