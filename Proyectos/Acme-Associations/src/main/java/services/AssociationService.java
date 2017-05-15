
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AssociationRepository;
import domain.Association;
import domain.User;

@Service
@Transactional
public class AssociationService {

	//managed repository ---------------------------------------
	@Autowired
	private AssociationRepository	associationRepository;

	//supporting services --------------------------------------
	@Autowired
	private RolesService			rolesService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	adminService;


	// Constructors --------------------------------------------
	public AssociationService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Association create() {
		Association created;
		created = new Association();
		return created;
	}

	public Association findOne(final int associationId) {
		Association retrieved;
		retrieved = this.associationRepository.findOne(associationId);
		return retrieved;
	}

	public Collection<Association> findAll() {
		Collection<Association> result;
		result = this.associationRepository.findAll();
		return result;
	}

	public Association save(final Association association) {
		Association result;
		result = this.associationRepository.save(association);
		return result;
	}

	public void delete(final Association association) {
		this.associationRepository.delete(association);
	}

	//Auxiliary methods ----------------------------------------
	public void flush() {
		this.associationRepository.flush();
	}

	public void banAssociation(final int associationId) {
		this.adminService.checkAdministrator();
		Association association;
		association = this.associationRepository.findOne(associationId);
		if (association.getAdminClosed())
			association.setAdminClosed(false);
		else
			association.setAdminClosed(true);
		this.associationRepository.save(association);
	}

	public void closeAssociationByManager(final int associationId) {
		final User user = this.userService.findByPrincipal();
		final Association association = this.associationRepository.findOne(associationId);
		this.rolesService.checkManager(user, association);
		if (association.getClosedAssociation())
			association.setClosedAssociation(false);
		else
			association.setClosedAssociation(true);
		this.associationRepository.save(association);
	}

	public Collection<Association> findAllExceptBannedAndClosed() {
		Collection<Association> result;
		result = this.associationRepository.findAllExceptBannedAndClosed();
		return result;
	}

	//Our other bussiness methods ------------------------------
	//	public Collection<Association> listByManager() {
	//		Collection<Association> result;
	//		User principal;
	//		principal = this.userService.findByPrincipal();
	//		final Collection<Roles> rols = this.rolesService.findAllByPrincipal();
	//		if (Roles.getType() == Roles.MANAGER) {
	//
	//		}
	//		result = this.associationRepository.findAllByPrincipal(principal.getId());
	//		return result;
	//	}
}
