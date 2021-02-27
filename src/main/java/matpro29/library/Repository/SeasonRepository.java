package matpro29.library.Repository;

import matpro29.library.Entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findAllByTitleId(String titleId);
}
