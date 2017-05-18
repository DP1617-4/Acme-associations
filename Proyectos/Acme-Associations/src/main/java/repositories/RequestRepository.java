
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.user.id = ?1")
	Collection<Request> findAllByUser(int userId);

	@Query("select r from Request r where r.association.id = ?1")
	Collection<Request> findAllByAssociation(int associationId);

	@Query("select r from Request r where r.user.id = ?1 and r.association.id=?2")
	Request findByRequestAndUser(int userId, int associationId);
}
