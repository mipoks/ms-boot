package design.kfu.controller.entry;

import design.kfu.entity.dto.PersonForm;
import design.kfu.helper.view.Alert;
import design.kfu.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController{

    private Alert info = new Alert();
    private static String headSuccess = "Регистрация прошла успешно!";
    private static String successRegistration = "Теперь Вы можете войти в аккаунт";

    private PersonForm personForm = new PersonForm();

    @Autowired
    private SignUpService signUpService;

    @GetMapping
    protected String getRegister(ModelMap modelMap) {
        modelMap.put("personForm", personForm);
        return "register";
    }

    @PostMapping
    protected String registerPost(@Valid PersonForm personForm, BindingResult bindingResult, ModelMap map, RedirectAttributes redirectAttributes) {
        log.info("PersonForm:" + personForm.toString());
        boolean err = false;
        if (bindingResult.hasErrors()) {
            err = true;

            StringBuilder stringBuilder = new StringBuilder();
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    stringBuilder.append(fieldError.getDefaultMessage());
                    stringBuilder.append('\n');
                } else {
                    if (object instanceof ObjectError) {
                        ObjectError objectError = (ObjectError) object;
                        stringBuilder.append(objectError.getDefaultMessage());
                        stringBuilder.append('\n');
                    }
                }
            }

            info.setBody(stringBuilder.toString());
        } else {
            personForm.setEmail(personForm.getEmail().toLowerCase());
            long ans = signUpService.signUp(personForm);

            if (ans == SignUpService.ALREADY_EXIST) {
                info.setBody("Пользователь с таким Email уже существует");
                err = true;
            }
            if (ans == SignUpService.UNKNOWN_ERROR) {
                info.setBody("Неизвестная ошибка");
                err = true;
            }
            if (ans == SignUpService.REGISTERED_WITH_ERROR || ans == SignUpService.SUCCESS) {
                info.setBody(successRegistration);
            }
            log.info("Registrating anwer: " + ans);
        }

        if (err) {
            info.setColor(Alert.COLOR_DANGER);
            info.setHead(Alert.HEAD_DANGER);
        } else {
            info.setColor(Alert.COLOR_SUCCESS);
            info.setHead(headSuccess);
        }
        redirectAttributes.addFlashAttribute("info", info);
        redirectAttributes.addFlashAttribute("personForm", personForm);
        return "redirect:/register";
    }
}
