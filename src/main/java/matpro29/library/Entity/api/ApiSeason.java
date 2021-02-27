package matpro29.library.Entity.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiSeason {
    private Integer season;
    private List<Result> episodes;
}
