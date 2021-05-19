package design.kfu.service.api.message;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.message.Container.LyricsGetContainer;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LyricsGetMessage {
    @SerializedName("message")
    private LyricsGetContainer container;
}
