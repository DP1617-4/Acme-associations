
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LoanRepository;
import domain.Association;
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

	@Autowired
	private RolesService	roleService;

	@Autowired
	private UserService		userService;


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

	public void delete(final Loan loan) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(loan);
		Assert.isTrue(loan.getId() != 0);
		this.roleService.checkCollaborator(principal, loan.getItem().getSection().getAssociation());

		this.loanRepository.delete(loan);
	}
	public Loan save(final Loan loan) {
		final User principal = this.userService.findByPrincipal();
		Association association;
		association = loan.getItem().getSection().getAssociation();
		this.roleService.checkCollaborator(principal, association);
		Assert.isTrue(this.userService.findAllByAssociation(association).contains(loan.getBorrower()));
		final Loan result = this.loanRepository.save(loan);

		return result;
	}

	public Loan reconstruct() {
		final Loan result = new Loan();

		return result;
	}

	public List<Loan> findOverdueLoans(final Pageable page) {
		List<Loan> result;

		result = this.loanRepository.findOverdueLoans(page);

		return result;
	}
}
