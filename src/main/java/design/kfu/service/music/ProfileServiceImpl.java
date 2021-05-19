package design.kfu.service.music;

import design.kfu.entity.music.Person;
import design.kfu.entity.dto.PersonForm;
import design.kfu.repository.music.PersonRepository;
import design.kfu.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private PersonRepository dbPerson;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long update(PersonForm personForm, Person person) {
        if (personForm.getSongCount() > 0) {
            person.setSongCount(personForm.getSongCount());
        }

        try {
            if (personForm.getPassword() != null && personForm.getPwd2() != null) {
                if (passwordEncoder.matches(personForm.getPassword(), person.getPassword())) {
                    person.setPassword(passwordEncoder.encode(personForm.getPwd2()));
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (personForm.getName() != null) {
            person.setName(personForm.getName());
        }

        return dbPerson.save(person).getId();
    }
}
