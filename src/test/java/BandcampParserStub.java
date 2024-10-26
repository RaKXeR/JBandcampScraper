import rakxer.jbandcampscraper.parser.old.BandcampParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class BandcampParserStub extends BandcampParser {

    private final String FILE_PATH;

    public BandcampParserStub(String filePath) {
        super();
        FILE_PATH = filePath;
    }

    @Override
    protected String getPage(String ignored) {
        try {
            // Read the contents of the file as a single String
            return new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        } catch (IOException e) {
            fail("Couldn't read the file: " + FILE_PATH);
            throw new RuntimeException("Unreachable code");
        }
    }

}
