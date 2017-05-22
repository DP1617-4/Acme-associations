
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	@Query("select m from Meeting m where m.association.id = ?1")
	Collection<Meeting> findAllByAssociation(int associationId);

}
