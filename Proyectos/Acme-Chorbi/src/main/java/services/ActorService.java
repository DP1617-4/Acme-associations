/*
 * ActorService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChorbiService			chorbiService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;
		result = this.actorRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public Actor findByUserAccount(final int userAccountId) {
		Actor result;
		result = this.actorRepository.findByUserAccount(userAccountId);
		return result;
	}

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;
		if (actor instanceof Administrator)
			result = this.administratorService.save((Administrator) actor);
		else
			result = this.chorbiService.save((Chorbi) actor);
		return result;
	}
	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = actor.getUserAccount();

		return result;
	}
}
