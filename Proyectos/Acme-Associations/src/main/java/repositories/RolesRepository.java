
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

	@Query("select r from Roles r where r.user.id = ?1 and r.association.id = ?2")
	Roles findRolesByUserAssociation(int userId, int associationId);

	@Query("select r from Roles r where r.user.id =?1")
	Collection<Roles> findAllByUser()(int userId);
}
