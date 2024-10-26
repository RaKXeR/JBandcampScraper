package rakxer.bandcamp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackInfo {
    String title;
    private File file;
    private Double duration;
}
