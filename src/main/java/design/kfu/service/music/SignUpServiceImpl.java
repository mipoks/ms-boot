package design.kfu.service.music;

import design.kfu.entity.music.Person;
import design.kfu.entity.dto.PersonForm;
import design.kfu.repository.music.PersonRepository;
import design.kfu.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void expel(Person person) {

        personRepository.delete(person);
    }

    @Override
    public long signUp(PersonForm personForm) {
        Person person = personRepository.findByEmail(personForm.getEmail());
        if (person != null) {
            return ALREADY_EXIST;
        }

        person = new Person(personForm.getName(), personForm.getEmail(),
                passwordEncoder.encode(personForm.getPassword()));
        personRepository.save(person);
        return SUCCESS;
    }
}
