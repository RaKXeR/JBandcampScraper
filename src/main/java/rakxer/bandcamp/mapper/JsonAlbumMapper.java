package rakxer.bandcamp.mapper;

import rakxer.bandcamp.model.Song;
import rakxer.bandcamp.dto.Album;
import rakxer.bandcamp.dto.TrackInfo;
import rakxer.bandcamp.model.SongBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping the JSON response Object from the Bandcamp API to the Song object.
 * Works essentially like a Song List factory.
 */
public class JsonAlbumMapper {

    public static List<Song> getSongs(Album album) {
        List<Song> songs = new ArrayList<>();
        for (TrackInfo track : album.getTrackInfo()) {
            songs.add(new SongBuilder()
                    .artist(album.getArtist())
                    .title(track.getTitle())
                    .streamingURL(track.getFile().getMp3_128())
                    .artId(album.getArtId())
                    .duration(track.getDuration())
                    .build()
            );
        }
        return songs;
    }

}
