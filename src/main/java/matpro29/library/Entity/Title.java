package matpro29.library.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Title {
    private String id;
    private Image image;
    private String runningTimeInMinutes;
    private String nextEpisode;
    private String numberOfEpisodes;
    private String seriesEndYea;
    private String seriesStartYear;
    private String title;
    private String titleType;
    private String year;
    private List<Principal> principals;

    private String legacyNameText;
    private String name;
    private KnowFor[] knownFor;
}
