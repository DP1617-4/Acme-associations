
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SanctionRepository;
import domain.Sanction;
import domain.User;

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

	@Autowired
	private UserService			userService;

	@Autowired
	private RolesService		rolesService;


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

	public void delete(final Sanction sanction) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(sanction);
		Assert.isTrue(sanction.getId() != 0);
		this.rolesService.checkCollaborator(principal, sanction.getAssociation());

		this.sanctionRepository.delete(sanction);
	}

	public Sanction save(final Sanction sanction) {
		final User principal = this.userService.findByPrincipal();
		this.rolesService.checkCollaborator(principal, sanction.getAssociation());
		Assert.isTrue(this.userService.findAllByAssociation(sanction.getAssociation()).contains(sanction.getUser()));
		final Sanction result = this.sanctionRepository.save(sanction);

		return result;
	}

	public Sanction reconstruct() {
		final Sanction result = new Sanction();

		return result;
	}

}
