package matpro29.library.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserTitle {
    @Id
    @GeneratedValue(generator = "userTitle_sequence")
    private Long userTitleId;

    @Column
    private String titleId;

    @Column
    private String imageUrl;
}
