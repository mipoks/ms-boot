package design.kfu.service.api.message;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.model.TrackData;
import lombok.Data;

@Data
public class TrackGetBody {
    @SerializedName("track")
    private TrackData track;
}
