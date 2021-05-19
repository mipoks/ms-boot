package design.kfu.service.api.message;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.message.Container.TrackGetContainer;
import lombok.Data;

@Data
public class TrackGetMessage {
    @SerializedName("message")
    private TrackGetContainer trackMessage;
}
