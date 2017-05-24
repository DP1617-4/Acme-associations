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
import java.util.List;

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

	@Query("select l.borrower from Loan l where l.item.id = ?1")
	Collection<User> findAllRelatedItem(int itemId);

	@Query("select s.user from Sanction s where s.association.id=?1 group by s.user order by count(s) DESC")
	Collection<User> selectUserWithMostSanctionsByAssociation(int associationId);

	@Query("select l.lender from Loan l where l.item.section.association.id=?1 group by l.lender order by count(l) DESC")
	Collection<User> findCollaboratorMostLoans(int associationId);

	@Query("select l.lender from Loan l where l.item.section.association.id=?1 group by l.lender order by count(l) ASC")
	Collection<User> findCollaboratorLeastLoans(int associationId);

	@Query("select count(l) from Loan l where l.item.section.association.id=?2 and l.lender.id=?1")
	Integer countLoansCollaborator(int userId, int associationId);

	//Dashboard queries

	// User 2.0

	//Dashboard queries Admin

	// El mínimo, el máximo y la media de miembros por asociación.
	@Query("select count(r)*1.0/(select count(a)*1.0 from Association a) from Roles r")
	Double avgMembers();

	// Usuarios con más sanciones.
	@Query("select u from User u where (select count(s) from Sanction s where s.user = u) >= ALL(select count(s) from Sanction s group by s.user)")
	Collection<User> mostSanctionedUsers();

	@Query("select count(r) from Roles r group by r.association order by count(r) ASC")
	List<Long> findCountMembers();
}
