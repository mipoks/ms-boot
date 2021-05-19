package design.kfu.service.api.message.Container;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.message.LyricsGetBody;
import design.kfu.service.api.model.Header;
import lombok.Data;

@Data
public class LyricsGetContainer {
    @SerializedName("body")
    private LyricsGetBody body;

    @SerializedName("header")
    private Header header;
}
