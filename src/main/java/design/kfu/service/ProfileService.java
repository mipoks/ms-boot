package design.kfu.service;

import design.kfu.entity.music.Person;
import design.kfu.entity.dto.PersonForm;

public interface ProfileService {
    int INCORRECT_PWD = -3;
    int UNKNOWN_ERROR = -2;
    int NOT_UPDATED = -1;
    int SUCCESS = 0;
    Long update(PersonForm personForm, Person person);
}
