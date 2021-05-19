package design.kfu.entity.chat;

import design.kfu.entity.music.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    private String id;
    private boolean isOpen;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Person> users = new LinkedHashSet<>();

    public String generateName() {
        StringBuilder name = new StringBuilder("Chat between: ");
        for (Person person : users) {
            name.append(person.getName()).append(" and ");
        }
        return name.delete(name.length() - 5, name.length() - 1).toString();
    }
}
