
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

	@Query("select p from Place p where p.id = ?1")
	Place findOne(int id);

}
