package matpro29.library.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Find {
    private List<Person> persons;
    private List<Title> titles;
}
