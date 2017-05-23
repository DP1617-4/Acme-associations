
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

	@Query("select a from Association a where a.adminClosed = false and a.closedAssociation = false")
	Collection<Association> findAllExceptBannedAndClosed();

	//· Las asociaciones que tengan +-10% de la media del número de productos.

	@Query("select r.association from Roles r group by r.association having count(r) > (select count(r)*0.9/(select count(a) from Association a) from Roles r) AND count(r) < (select count(r)*1.1/(select count(a) from Association a) from Roles r)")
	Collection<Association> findAssociationsAroundAVGMembers();

	// · Asociaciones con más prestamos en el último mes.
	@Query("select l.item.section.association from Loan l group by l.item.section.association having count(l) >= ALL(select count(l) from Loan l group by l.item.section.association)")
	Collection<Association> findMostLoansAssociation();

	//· Asociaciones que más han sancionado.
	@Query("select s.association from Sanction s group by s.association having count(s) >= ALL(select count(s) from Sanction s group by s.association)")
	Collection<Association> findMostSanctionsAssociation();

	//· Asociaciones inactivas más de 3 meses, o inactivas desde su creación.
	@Query("select ass from Association ass where (select count(a) from Activity a where a.association = ass and datediff(current_date,a.endMoment) < 90) = 0")
	Collection<Association> inactiveAssociations();

}
