package matpro29.library.Mapper;

import matpro29.library.Entity.Find;
import matpro29.library.Entity.Person;
import matpro29.library.Entity.Title;
import matpro29.library.Entity.api.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindMapper {
    public Find findMapper(Result[] results) {
        List<Person> persons = new ArrayList<>();
        List<Title> titles = new ArrayList<>();

        for (Result result : results) {
            if (result.getTitle() != null) {
                titles.add(titleMapper(result));
            } else {
                persons.add(personMapper(result));
            }
        }

        Find find = new Find();
        find.setPersons(persons);
        find.setTitles(titles);

        return find;
    }

    private Person personMapper(Result result) {
        Person person = new Person();
        person.setId(result.getId());
        person.setName(result.getName());
        person.setImage(result.getImage());

        return person;
    }

    private Title titleMapper(Result result) {
        Title title = new Title();
        title.setId(result.getId());
        title.setTitle(result.getTitle());
        title.setTitleType(result.getTitleType());
        title.setImage(result.getImage());

        return title;
    }
}
