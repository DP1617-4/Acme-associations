
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LoanRepository;
import domain.Loan;
import domain.User;

@Service
@Transactional
public class LoanService {

	public LoanService() {
		super();
	}


	// Repository

	@Autowired
	private LoanRepository	loanRepository;


	// Auxiliary services

	public Loan create() {
		Loan result;

		result = new Loan();

		return result;
	}

	public Loan findOne(final int loanId) {
		Loan result;

		result = this.loanRepository.findOne(loanId);

		return result;
	}

	public Collection<Loan> findAll() {
		Collection<Loan> result;

		result = this.loanRepository.findAll();

		return result;
	}

	public void delete(final Loan Loan) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(Loan);
		Assert.isTrue(Loan.getId() != 0);
		Assert.isTrue(this.rolesService.checkCollaborator(principal, Loan.getSection().getAssociation()));

		this.loanRepository.delete(Loan);
	}

	public Loan save(final Loan Loan) {
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(this.rolesService.checkCollaborator(principal, Loan.getSection().getAssociation()));
		Assert.isTrue(this.userService.findByAssociation().contains(loan.getBorrower));
		final Loan result = this.loanRepository.save(Loan);

		return result;
	}

	public Loan reconstruct() {
		final Loan result = new Loan();

		return result;
	}

}
