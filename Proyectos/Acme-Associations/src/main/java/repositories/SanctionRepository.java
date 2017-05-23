
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sanction;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

	@Query("select s from Sanction s where s.association.id = ?1 and s.user.id = ?2")
	Collection<Sanction> findByAssociationAndUser(int associationId, int userId);

	@Query("select s from Sanction s where s.user.id = ?1")
	Collection<Sanction> findAllByPrincipal(int userId);

	@Query("select s from Sanction s where s.association.id = ?1 and s.user.id = ?2 and s.endDate > now()")
	Collection<Sanction> findByAssociationAndUserActive(int associationId, int userId);

	@Query("select s from Sanction s where s.user.id = ?1 and s.endDate > now()")
	Collection<Sanction> findAllByPrincipalActive(int userId);
	
}
