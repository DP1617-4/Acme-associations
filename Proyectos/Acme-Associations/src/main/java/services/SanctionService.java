
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SanctionRepository;
import domain.Sanction;

@Service
@Transactional
public class SanctionService {

	public SanctionService() {
		super();
	}


	// Repository

	@Autowired
	private SanctionRepository	sanctionRepository;


	// Auxiliary services

	public Sanction create() {
		Sanction result;

		result = new Sanction();

		return result;
	}

	public Sanction findOne(final int SanctionId) {
		Sanction result;

		result = this.sanctionRepository.findOne(SanctionId);

		return result;
	}

	public Collection<Sanction> findAll() {
		Collection<Sanction> result;

		result = this.sanctionRepository.findAll();

		return result;
	}

	public void delete(final Sanction Sanction) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(Sanction);
		Assert.isTrue(Sanction.getId() != 0);
		Assert.isTrue(this.rolesService.checkCollaborator(principal, Sanction.getSection().getAssociation()));

		this.sanctionRepository.delete(Sanction);
	}

	public Sanction save(final Sanction Sanction) {
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(this.rolesService.checkCollaborator(principal, Sanction.getSection().getAssociation()));
		Assert.isTrue(this.userService.findByAssociation().contains(sanction.getBorrower));
		final Sanction result = this.sanctionRepository.save(Sanction);

		return result;
	}

	public Sanction reconstruct() {
		final Sanction result = new Sanction();

		return result;
	}

}
