package design.kfu.service.api.message.Container;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.message.TrackGetBody;
import design.kfu.service.api.model.Header;
import lombok.Data;

@Data
public class TrackGetContainer {
    @SerializedName("header")
    private Header header;

    @SerializedName("body")
    private TrackGetBody body;
}
