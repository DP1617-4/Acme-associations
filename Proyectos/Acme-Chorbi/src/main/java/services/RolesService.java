
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private UserService		actorService;


	//CRUD

	public Roles create(final User user, final Association association) {
		final Roles result = new Roles();
		result.setUser(user);
		result.setAssociation(association);
		return result;
	}

	public Roles assignRoles(final User user, final Association association, final String roleType) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId, association.getId);
		this.checkManager(user, association);

		if (role == null)
			role = this.create(user, association);

		role.setType = roleType;

	}

	public Roles save(final Roles role) {
		Roles result;
		result = this.roleRepository.save(role);
		return result;
	}

	public Roles findOne(final int roleId) {

		Roles role;
		role = this.roleRepository.findOne(roleId);
		return role;
	}

	public Collection<Roles> findAllByPrincipal() {
		final User principal;
		Collection<Roles> result;
		user = this.userService.findByPrincipal();
		result = this.roleRepository.findAllByUser(user.getId());
		return result;
	}

	//Business Methods

	public void checkManager(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId, association.getId);
		Assert.isTrue(role.getType.equals("MANAGER"));

	}

	public void checkCollaborator(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId, association.getId);
		Assert.isTrue(role.getType.equals("MANAGER") || role.getType.equals("COLLABORATOR"));

	}

	public void checkAssociate(final User user, final Association association) {

		Roles role;
		role = this.roleRepository.findRolesByUserAssociation(user.getId, association.getId);
		Assert.notNull(role);

	}

	public void flush() {
		this.roleRepository.flush();
	}

}
