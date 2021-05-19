package design.kfu.service;

import design.kfu.entity.music.Person;
import design.kfu.config.security.details.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
@Slf4j
public class PersonGiver {
    public static Person get() {
        Person person = null;
        try {
            person = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception ex) {
        }
        return person;
    }

    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
