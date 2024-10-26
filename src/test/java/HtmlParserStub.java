import rakxer.bandcamp.parser.HtmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

public class HtmlParserStub implements HtmlParser {

    private final String FILE_PATH;

    public HtmlParserStub(String filePath) {
        super();
        FILE_PATH = filePath;
    }

    @Override
    public String getPage(String ignored) {
        try {
            return Files.readString(Path.of(FILE_PATH));
        } catch (IOException e) {
            fail("Couldn't read the file: " + FILE_PATH);
            throw new RuntimeException("Unreachable code");
        }
    }

}
