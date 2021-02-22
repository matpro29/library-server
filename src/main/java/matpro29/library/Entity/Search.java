package matpro29.library.Entity;

import lombok.Data;

@Data
public class Search {
    private Item[] Search;
    private String totalResults;
    private String Response;
}
