package design.kfu.service.music;

import design.kfu.entity.chat.PersonInfo;
import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.music.Person;
import design.kfu.repository.chat.PersonInfoRepository;
import design.kfu.repository.music.PersonRepository;
import design.kfu.service.PersonGiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonInfoRepository personInfoRepository;

    @Autowired
    private PersonRepository personRepository;

    public PersonInfo getPersonInfo(long personId) {
        Person person = PersonGiver.get();
        Optional<PersonInfo> personInfoOptional = personInfoRepository.findByPersonId(personId);
        if (personInfoOptional.isEmpty()) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setPerson(person);
            return personInfoRepository.save(personInfo);
        } else {
            return personInfoOptional.get();
        }
    }
}
