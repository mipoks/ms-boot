package design.kfu.service.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class Track {
    @SerializedName("track")
    private TrackData track;
}
