
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Association;
import domain.Request;
import domain.Roles;
import domain.User;

@Service
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository	requestRepository;

	//supporting services-------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private RolesService		rolesService;


	//Basic CRUD methods-------------------

	public Request create(final Association association) {

		User principal;
		Request created;

		principal = this.userService.findByPrincipal();
		created = new Request();
		created.setUser(principal);
		created.setAssociation(association);
		return created;
	}

	public Request findOne(final int requestId) {

		Request retrieved;
		retrieved = this.requestRepository.findOne(requestId);
		return retrieved;
	}

	public Collection<Request> findAll() {

		return this.requestRepository.findAll();
	}

	public Request save(final Request request) {

		final Roles roles = this.rolesService.findRolesByPrincipalAssociation(request.getAssociation());

		Assert.isTrue(this.isRequestedByPrincipal(request.getAssociation()) == false, "request.requested");
		Assert.isTrue(roles == null, "request.inside");

		Request saved;

		saved = this.requestRepository.save(request);
		return saved;

	}
	public void delete(final Request request) {

		this.requestRepository.delete(request.getId());

	}

	//Auxiliary methods

	//Our other bussiness methods

	public void flush() {
		this.requestRepository.flush();

	}

	public Collection<Request> findAllByPrincipal() {
		Collection<Request> result;
		final User user = this.userService.findByPrincipal();
		result = this.requestRepository.findAllByUser(user.getId());
		return result;
	}

	public Collection<Request> findAllByAssociation(final Association association) {

		Collection<Request> result;
		this.rolesService.checkManagerPrincipal(association);
		result = this.requestRepository.findAllByAssociation(association.getId());
		return result;
	}
	public void accept(final Request request) {

		final User user;
		final Association association;

		user = request.getUser();
		association = request.getAssociation();

		this.messageService.sendAccepted(request);
		this.rolesService.assignRoles(user, association, Roles.ASSOCIATE);
		this.delete(request);

	}

	public void deny(final Request request) {

		final User user;
		final Association association;

		user = request.getUser();
		association = request.getAssociation();

		this.messageService.sendDenied(request);
		this.delete(request);

	}

	public Boolean isRequested(final User user, final Association association) {

		Boolean res;
		res = true;

		final Request request = this.requestRepository.findByRequestAndUser(user.getId(), association.getId());

		if (request == null)
			res = false;

		return res;
	}

	public Boolean isRequestedByPrincipal(final Association association) {

		final User principal = this.userService.findByPrincipal();

		return this.isRequested(principal, association);
	}
}
