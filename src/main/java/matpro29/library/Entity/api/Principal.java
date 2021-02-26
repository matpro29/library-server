package matpro29.library.Entity.api;

import lombok.Data;

@Data
public class Principal {
    private String id;
    private String legacyNameText;
    private String name;
    private String category;
    private String[] characters;
    private String endYear;
    private String episodeCount;
    private Character[] roles;
    private String startYear;
    private String job;
}
