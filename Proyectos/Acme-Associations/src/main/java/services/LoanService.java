
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private Validator		validator;

	@Autowired
	private ItemService		itemService;


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
		Assert.isTrue(this.itemService.isLoanable(loan.getItem()));
		this.roleService.checkCollaborator(principal, association);
		this.roleService.checkAssociate(loan.getBorrower(), association);
		final Loan result = this.loanRepository.save(loan);

		return result;
	}

	public Loan reconstruct(final Loan loan, final BindingResult binding) {

		loan.setLender(this.userService.findByPrincipal());
		loan.setStartDate(new Date(System.currentTimeMillis() - 1));

		this.validator.validate(loan, binding);
		return loan;
	}

	public List<Loan> findOverdueLoans(final Pageable page) {
		List<Loan> result;

		result = this.loanRepository.findOverdueLoans(page);

		return result;
	}

	public List<Loan> findPendingByAssociation(final Association association) {
		List<Loan> result;

		result = this.loanRepository.findPendingByAssociation(association.getId());

		return result;
	}

	public Collection<Loan> findUnfinishedLoansItem(final int itemId) {
		Collection<Loan> result;

		result = this.loanRepository.findUnfinishedLoans(itemId);

		return result;
	}

	public List<Loan> findByAssociation(final Association association) {
		List<Loan> result;

		result = this.loanRepository.findByAssociation(association.getId());

		return result;
	}

	public List<Loan> findByUser(final User user) {
		List<Loan> result;

		result = this.loanRepository.findByUser(user.getId());

		return result;
	}

	public Loan end(final Loan loan) {
		Loan result;
		final Date currentTime = new Date(System.currentTimeMillis() - 1);
		loan.setFinalDate(currentTime);
		result = this.loanRepository.save(loan);
		Assert.isTrue(result.getFinalDate().equals(currentTime));

		return result;
	}

	public Object[] minMaxAvgLoans() {
		final Object[] result = new Object[3];

		result[0] = this.loanRepository.avgLoans();
		final List<Long> counts = this.loanRepository.findCountLoans();
		result[1] = counts.get(0);
		result[2] = counts.get(counts.size() - 1);

		return result;
	}
}
