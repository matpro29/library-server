package matpro29.library.Entity.api;

import lombok.Data;

@Data
public class KnowFor {
    private Principal[] cast;
    private Principal[] crew;
    private Summary summary;
    private String id;
    private String title;
    private String titleType;
    private String year;
}
