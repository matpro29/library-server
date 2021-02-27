package matpro29.library.Repository;

import matpro29.library.Entity.UserTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface UserTitleRepository extends JpaRepository<UserTitle, Long> {
    UserTitle findByTitleId(String titleId);
}
