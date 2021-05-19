package design.kfu.service.api.message.Container;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.model.Header;
import lombok.Data;

@Data
public class ErrorMessageContainer {
    @SerializedName("body")
    private String body;

    @SerializedName("header")
    private Header header;
}
