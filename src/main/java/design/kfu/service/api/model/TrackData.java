package design.kfu.service.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class TrackData {
    @SerializedName("track_id")
    private Integer trackId;

    @SerializedName("track_mbid")
    private String trackMbid;

    @SerializedName("track_isrc")
    private String trackIsrc;

    @SerializedName("track_spotify_id")
    private String trackSpotifyId;

    @SerializedName("track_soundcloud_id")
    private String trackSoundcloudId;

    @SerializedName("track_xboxmusic_id")
    private String trackXboxmusicId;

    @SerializedName("track_name")
    private String trackName;

    @SerializedName("track_name_translation_list")
    private List<Object> trackNameTranslationList = null;
    @SerializedName("track_rating")
    private Integer trackRating;

    @SerializedName("track_length")
    private Integer trackLength;

    @SerializedName("commontrack_id")
    private Integer commontrackId;

    @SerializedName("instrumental")
    private Integer instrumental;

    @SerializedName("explicit")
    private Integer explicit;

    @SerializedName("has_lyrics")
    private Integer hasLyrics;

    @SerializedName("has_subtitles")
    private Integer hasSubtitles;

    @SerializedName("num_favourite")
    private Integer numFavourite;

    @SerializedName("lyrics_id")
    private Integer lyricsId;

    @SerializedName("subtitle_id")
    private Integer subtitleId;

    @SerializedName("album_id")
    private Integer albumId;

    @SerializedName("album_name")
    private String albumName;

    @SerializedName("artist_id")
    private Integer artistId;

    @SerializedName("artist_mbid")
    private String artistMbid;

    @SerializedName("artist_name")
    private String artistName;

    @SerializedName("album_coverart_100x100")
    private String albumCoverart100x100;

    @SerializedName("album_coverart_350x350")
    private String albumCoverart350x350;

    @SerializedName("album_coverart_500x500")
    private String albumCoverart500x500;

    @SerializedName("album_coverart_800x800")
    private String albumCoverart800x800;

    @SerializedName("track_share_url")
    private String trackShareUrl;

    @SerializedName("track_edit_url")
    private String trackEditUrl;

    @SerializedName("commontrack_vanity_id")
    private String commontrackVanityId;

    @SerializedName("restricted")
    private Integer restricted;

    @SerializedName("first_release_date")
    private String firstReleaseDate;

    @SerializedName("updated_time")
    private String updatedTime;

}
