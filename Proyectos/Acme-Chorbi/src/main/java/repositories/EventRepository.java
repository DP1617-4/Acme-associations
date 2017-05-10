
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	//Queries de la dashboard
	@Query("select e from Event e where current_date < e.moment and datediff(e.moment,current_date) < 30 and e.registered.size < e.numberSeat")
	Collection<Event> findAllEventsInOneMonth();
	
	@Query("select e from Event e join e.registered c where c.id = ?1")
	Collection<Event> findAllByChorbi(int chorbiId);

	@Query("select e from Event e where e.organiser.id = ?1")
	Collection<Event> findAllByPrincipal(int id);

}
