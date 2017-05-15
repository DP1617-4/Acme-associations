
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Association;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Integer> {

	@Query("select a from Association a where a.id = ?1")
	Association findOne(int id);

	@Query("select a from Association a where a.closedAdmin = false and a.closedAssociation = false")
	Collection<Association> findAllExceptBannedAndClosed();

	@Query("select a from Association a where a.closedAdmin = false")
	Collection<Association> findAllByManager();

}
