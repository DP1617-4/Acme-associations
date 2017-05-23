
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query("select a from Activity a where a.id = ?1")
	Activity findOne(int id);

	@Query("select a from Activity a where a.association.id = ?1")
	Collection<Activity> findAllByAssociation(int associationId);

	@Query("select a from Activity a where a.association.id=?1 order by a.attendants.size DESC")
	Collection<Activity> findMostAttendedByAssociation(int associationId);
}
