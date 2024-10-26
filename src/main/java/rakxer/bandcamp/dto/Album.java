package rakxer.bandcamp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {
    private String artist;
    @JsonProperty("art_id")
    private String artId;
    @JsonProperty("trackinfo")
    private List<TrackInfo> trackInfo;
}
