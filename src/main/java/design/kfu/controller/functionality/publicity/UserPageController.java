package design.kfu.controller.functionality.publicity;

import design.kfu.service.music.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
public class UserPageController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public String getProfileTemplate(@PathVariable long id, ModelMap modelMap) {
        modelMap.put("userInfo", personService.getPersonInfo(id));
        return "user";
    }
}
