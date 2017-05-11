
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RequestRepository;
import domain.Association;
import domain.Request;
import domain.User;

@Service
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository	requestRepository;

	//supporting services-------------------
	@Autowired
	private UserService			userService;


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
		Request saved;
		saved = this.requestRepository.save(request);
		return saved;

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
		result = this.requestRepository.findAllByAssociation(association.getId());
		return result;
	}

}