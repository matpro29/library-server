package matpro29.library.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Season {
    @Id
    @GeneratedValue(generator = "season_sequence")
    private Long seasonId;

    @Column
    private String titleId;

    @Column
    private Integer season;

    @Column
    private Integer episodes;
}
