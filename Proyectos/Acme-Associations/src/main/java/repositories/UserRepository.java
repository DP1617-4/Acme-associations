/*
 * ActorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("Select c from User c where c.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	//	@Query("select r.user from Roles r where r.association.id = ?1")
	//	Collection<User> findAllByAssociation(int associationId, Pageable pageRequest);

	@Query("select r.user from Roles r where r.association.id = ?1")
	Collection<User> findAllByAssociation(int associationId);

	@Query("select r.user from Roles r where r.association.id = ?1 and r.type='MANAGER'")
	User findAssociationManager(int associationId);

	@Query("select r.user from Roles r where r.type='COLLABORATOR' and r.association.id=?1")
	Collection<User> findAssociationCollaborators(int associationId);

	//Dashboard queries

	// User 2.0

}
