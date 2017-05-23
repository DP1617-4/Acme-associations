
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SanctionRepository;
import domain.Association;
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
	private SanctionRepository		sanctionRepository;

	// Auxiliary services

	@Autowired
	private UserService				userService;

	@Autowired
	private RolesService			rolesService;

	@Autowired
	private AdministratorService	administratorService;


	public Sanction create(final int userId, final Association association) {
		Sanction result;
		final User user = userService.findOne(userId);

		result = new Sanction();
		result.setUser(user);
		result.setAssociation(association);

		return result;
	}

	public Sanction findOne(final int SanctionId) {
		Sanction result;

		result = this.sanctionRepository.findOne(SanctionId);

		return result;
	}

	public Sanction findOneToEdit(final int SanctionId) {
		Sanction result;
		result = this.sanctionRepository.findOne(SanctionId);
		if (!administratorService.checkAdministratorBool()) {
			rolesService.checkCollaboratorPrincipal(result.getAssociation());
		}

		rolesService.checkCollaboratorPrincipal(result.getAssociation());

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

	public Collection<Sanction> findByAssociationAndUser(Association association, int userId) {
		if (!administratorService.checkAdministratorBool()) {
			rolesService.checkCollaboratorPrincipal(association);
		}
		rolesService.checkCollaboratorPrincipal(association);
		final Collection<Sanction> result = sanctionRepository.findByAssociationAndUser(association.getId(), userId);
		return result;
	}
	public Collection<Sanction> findByAssociationAndPrincipal(Association association) {
		final Collection<Sanction> result = sanctionRepository.findByAssociationAndUser(association.getId(), userService.findByPrincipal().getId());
		return result;
	}

	public Collection<Sanction> findAllByPrincipal() {
		final Collection<Sanction> result = sanctionRepository.findAllByPrincipal(userService.findByPrincipal().getId());
		return result;
	}

	public Collection<Sanction> findByAssociationAndPrincipalActive(Association association) {
		final Collection<Sanction> result = sanctionRepository.findByAssociationAndUserActive(association.getId(), userService.findByPrincipal().getId());
		return result;
	}

	public Collection<Sanction> findByAssociationAndUserActive(Association association, int userId) {
		if (!administratorService.checkAdministratorBool()) {
			rolesService.checkCollaboratorPrincipal(association);
		}
		rolesService.checkCollaboratorPrincipal(association);
		final Collection<Sanction> result = sanctionRepository.findByAssociationAndUserActive(association.getId(), userId);
		return result;
	}
	public Collection<Sanction> findAllByPrincipalActive() {
		final Collection<Sanction> result = sanctionRepository.findAllByPrincipalActive(userService.findByPrincipal().getId());
		return result;
	}

	public Integer countSanctionsByUserAssociation(User user, Association association) {

		return this.sanctionRepository.countSanctionsByUserAssociation(user.getId(), association.getId());
	}

}
