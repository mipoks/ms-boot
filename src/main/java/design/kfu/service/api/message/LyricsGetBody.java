package design.kfu.service.api.message;

import com.google.gson.annotations.SerializedName;
import design.kfu.service.api.model.Lyrics;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LyricsGetBody {
    @SerializedName("lyrics")
    private Lyrics lyrics;
}
