
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query("select a from Activity a where a.id = ?1")
	Activity findOne(int id);

}
