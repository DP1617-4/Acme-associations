
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

	////	Actividades en curso con más usuarios apuntados.

	@Query("select a from Activity a where current_date between a.startMoment and a.endMoment AND a.attendants.size >= ALL(select a.attendants.size from Activity a)")
	Collection<Activity> activeActivitiesWithMostUsers();
}
