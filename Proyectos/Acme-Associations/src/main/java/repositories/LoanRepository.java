
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

}
