
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AssociationRepository;
import domain.Association;
import domain.Roles;
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
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		Association created;

		created = new Association();
		created.setCreationDate(new Date(System.currentTimeMillis() - 100));
		created.setAdminClosed(false);
		created.setClosedAssociation(false);
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
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		Association result;
		if (association.getId() == 0) {
			association.setCreationDate(new Date(System.currentTimeMillis() - 100));
			result = this.associationRepository.save(association);
			this.rolesService.assignRoles(user, result, Roles.MANAGER);
		} else {
			this.rolesService.checkManager(user, association);
			result = this.associationRepository.save(association);
		}
		return result;
	}

	public void delete(final Association association) {
		this.associationRepository.delete(association);
	}

	//Auxiliary methods ----------------------------------------
	public void flush() {
		this.associationRepository.flush();
	}

	//Our other bussiness methods ------------------------------
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

	public void checkClosedBanned(final Association association) {

		Assert.isTrue(association.getAdminClosed() == false && association.getClosedAssociation() == false, "association.closed.error");
	}
	
	public Association getRandomAssociation(){
		
		Association result;
		int randomNum;
		Random rn = new Random();
		Collection<Association> associations = this.findAllExceptBannedAndClosed();
		
		randomNum = rn.nextInt(associations.size());
		List<Association> assList = new ArrayList<Association>(associations);
		
		result = assList.get(randomNum);
		
		return result;
	}
	

}
