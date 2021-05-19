package design.kfu.helper.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    public final static String COLOR_SUCCESS = "success";
    public final static String HEAD_DANGER = "Произошла ошибка!";
    public final static String COLOR_DANGER = "danger";

    private String body;
    private String color = "light";
    private String head;

}
