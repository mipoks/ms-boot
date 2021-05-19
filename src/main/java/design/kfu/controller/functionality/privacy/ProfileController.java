package design.kfu.controller.functionality.privacy;

import design.kfu.entity.music.Person;
import design.kfu.entity.dto.PersonForm;
import design.kfu.helper.view.Alert;
import design.kfu.service.PersonGiver;
import design.kfu.service.ProfileService;
import design.kfu.service.SignUpService;
import design.kfu.service.music.SignUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class ProfileController {

    private static String headSuccess = "Настройки сохранены!";
    private static String bodySuccess = "Изменения вступили в силу";
    private static String bodyDanger = "Изменения не сохранены. Попробуйте позднее";

    private Alert info = new Alert();

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String getProfile(ModelMap map) {
        Person person = PersonGiver.get();
        map.put("user", person);
        map.put("title", "Настройки");
        return "profile";
    }

    @PostMapping("/profile")
    public String changeProfile(ModelMap map, @RequestParam(required = false) String passworddelete, HttpServletRequest req,
                                Locale locale) {
        Person person = PersonGiver.get();

        String title = messageSource.getMessage("profile.title", new Object[]{}, locale);
        map.put("title", title);

        boolean del = false;
        boolean error = false;

        PersonForm personForm = new PersonForm();

        if (passworddelete != null)
            del = true;
        if (passworddelete != null && !passworddelete.equals("")) {
            try {
                if (passwordEncoder.matches(passworddelete, person.getPassword())) {
                    signUpService.expel(person);
                    PersonGiver.logout();
                    return "redirect:" + req.getContextPath() + "/search";
                } else {
                    error = true;
                    info.setBody("Старый пароль указан неверно");
                }
            } catch (Exception e) {
                error = true;
                info.setBody("Неизвестная ошибка. Попробуйте позже");
                e.printStackTrace();
            }
        }

        if (!del) {
            String sc = req.getParameter("songcount");
            String passwordOld = req.getParameter("passwordold");
            String passwordNew = req.getParameter("passwordnew");
            String newname = req.getParameter("newname");

            if (sc != null && !sc.equals("")) {
                try {
                    int songCount = Integer.parseInt(sc);
                    if (songCount > 0 && songCount < 20) {
                        personForm.setSongCount(songCount);
                    } else {
                        info.setBody("Кол-во песен должно быть больше 0, но меньше 20");
                    }
                } catch (Exception ex) {
                    error = true;
                    info.setBody("Пожалуйста, введите число");
                }
            }

            if (passwordNew != null && passwordOld != null && !passwordOld.equals("") && !passwordNew.equals("")) {
                if (passwordNew.length() >= 6) {
                    personForm.setPassword(passwordOld);
                    personForm.setPwd2(passwordNew);
                } else {
                    error = true;
                    info.setBody("Новый пароль слишком короткий!");
                }
            }

            if (newname != null && !newname.equals("")) {
                if (newname.length() >= 2) {
                    personForm.setName(newname);
                } else {
                    error = true;
                    info.setBody("Имя слишком короткое");
                }
            }

            long ans = profileService.update(personForm, person);

            if (ans == ProfileService.NOT_UPDATED) {
                error = true;
                info.setBody("Не удалось сохранить");
            }
            if (ans == ProfileService.UNKNOWN_ERROR) {
                error = true;
                info.setBody("Проблемы с сохранением. Попробуйте позже");
            }
            if (ans == ProfileService.INCORRECT_PWD) {
                error = true;
                info.setBody("Старый пароль указан неверно");
            }

        }
        if (error) {
            if (info.getBody() == null)
                info.setBody(bodyDanger);
            info.setHead(Alert.HEAD_DANGER);
            info.setColor(Alert.COLOR_DANGER);
        } else {
            info.setBody(bodySuccess);
            info.setHead(headSuccess);
            info.setColor(Alert.COLOR_SUCCESS);
        }

        map.put("user", person);
        map.put("info", info);
        return "profile";
    }
}
