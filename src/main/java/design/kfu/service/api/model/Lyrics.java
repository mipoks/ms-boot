package design.kfu.service.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Lyrics {
    @SerializedName("lyrics_body")
    private String lyricsBody;

    @SerializedName("lyrics_copyright")
    private String lyricsCopyright;

    @SerializedName("lyrics_id")
    private int lyricsId;

    @SerializedName("lyrics_language")
    private String lyricsLanguage;

    @SerializedName("pixel_tracking_url")
    private String pixelTrackingURL;

    @SerializedName("script_tracking_url")
    private String scriptTrackingURL;
}
