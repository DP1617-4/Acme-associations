
package repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	@Query("select l from Loan l where floor(datediff(Current_date, l.expectedDate)) >= 1 AND l.finalDate = null")
	List<Loan> findOverdueLoans(Pageable page);

	@Query("select l from Loan l where l.finalDate = null AND l.item.section.association.id = ?1")
	List<Loan> findPendingByAssociation(int id);

	@Query("select l from Loan l where l.item.section.association.id = ?1")
	List<Loan> findByAssociation(int id);

	@Query("select l from Loan l where l.borrower.id = ?1 AND l.finalDate = null")
	List<Loan> findByUser(int id);

	//Dashboard
	// · El mínimo, el máximo y la media de préstamos por asociación.
	@Query("select count(r)*1.0/(select count(a)*1.0 from Association a) from Loan r")
	Double avgLoans();

	@Query("select count(r) from Loan r right join r.item.section.association a group by r.item.section.association order by count(r) ASC")
	List<Long> findCountLoans();

}
