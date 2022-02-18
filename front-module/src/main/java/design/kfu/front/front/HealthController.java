package design.kfu.front.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Daniyar Zakiev
 */
@Controller
public class HealthController {

    @GetMapping("/check")
    public @ResponseBody String answer() {
        StringBuilder answer = new StringBuilder("Фронт запущен. \n Проверяю бэк:\n");
        try {
            URL yahoo = new URL("http://localhost:8080/health");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                answer.append(inputLine).append("\n");
            in.close();
        } catch (Exception e) {
            answer.append(e.getMessage());
        }
        return answer.toString();
    }
}
