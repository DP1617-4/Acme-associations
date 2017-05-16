
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RolesRepository;
import domain.Association;
import domain.Roles;
import domain.User;

@Service
@Transactional
public class RolesService {

	//Constructor
	public RolesService() {
		super();
	}


	//Managed Repository

	@Autowired
	private RolesRepository	roleRepository;

	//Auxiliary Services

	@Autowired
	private UserService		userService;


	//CRUD

	public Roles create(final User user, final Association association) {
		final Roles result = new Roles();
		result.setUser(user);
		result.setAssociation(association);
		return result;
	}

	public Roles assignRoles(final User user, final Association association, final String roleType) {

		this.checkManager(user, association);

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId(), association.getId());

		if (role == null)
			role = this.create(user, association);

		role.setType(roleType);
		final Roles saved = this.save(role);

		return saved;

	}

	public Roles findRolesByUserAssociation(final User user, final Association association) {

		return this.roleRepository.findRolesByUserAssociation(user.getId(), association.getId());
	}

	public Roles findRolesByPrincipalAssociation(final Association association) {

		User principal;

		principal = this.userService.findByPrincipal();

		return this.roleRepository.findRolesByUserAssociation(principal.getId(), association.getId());
	}

	public Roles save(final Roles role) {
		Roles result;
		result = this.roleRepository.save(role);
		return result;
	}

	public void delete(final Roles role) {

		this.roleRepository.delete(role);
	}

	public Roles findOne(final int roleId) {

		Roles role;
		role = this.roleRepository.findOne(roleId);
		return role;
	}

	public Collection<Roles> findAllByPrincipal() {
		final User principal;
		Collection<Roles> result;
		principal = this.userService.findByPrincipal();
		result = this.roleRepository.findAllByUser(principal.getId());
		return result;
	}

	public Collection<Roles> findAllByUser(final User user) {
		Collection<Roles> result;
		result = this.roleRepository.findAllByUser(user.getId());
		return result;
	}

	//Business Methods

	public void checkManager(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId(), association.getId());
		Assert.isTrue(role.getType().equals("MANAGER"), "association.role.manager.error");

	}

	public void checkCollaborator(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId(), association.getId());
		Assert.isTrue(role.getType().equals("MANAGER") || role.getType().equals("COLLABORATOR"), "association.role.collaborator.error");

	}

	public void checkAssociate(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId(), association.getId());
		Assert.notNull(role, "association.role.associate.error");

	}
	public void flush() {
		this.roleRepository.flush();
	}

}
