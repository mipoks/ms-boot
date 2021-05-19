package design.kfu.controller.entry;

import design.kfu.helper.view.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLogin(@RequestParam(value = "error", required = false) String error, ModelMap modelMap) {
        if (error != null) {
            log.info("Login failed");
            Alert alert = new Alert();
            alert.setBody("Неверный логин или пароль");
            alert.setColor(Alert.COLOR_DANGER);
            alert.setHead(Alert.HEAD_DANGER);
            modelMap.put("info", alert);
        }
        return "login";
    }
}
