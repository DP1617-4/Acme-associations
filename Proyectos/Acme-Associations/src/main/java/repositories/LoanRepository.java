
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
