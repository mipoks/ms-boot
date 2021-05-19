package design.kfu.service.api.message;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.message.Container.ErrorMessageContainer;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessage {
    @SerializedName("message")
    private ErrorMessageContainer messageContainer;
}
