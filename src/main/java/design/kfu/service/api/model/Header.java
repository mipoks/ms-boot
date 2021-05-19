package design.kfu.service.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Header {
    @SerializedName("status_code")
    private Integer statusCode;
    @SerializedName("execute_time")
    private Double executeTime;
    @SerializedName("confidence")
    private Integer confidence;
    @SerializedName("mode")
    private String mode;
    @SerializedName("cached")
    private Integer cached;
}
