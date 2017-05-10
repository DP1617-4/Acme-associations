/*
 * CreditHolderService.java
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

import repositories.CreditHolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditHolder;

@Service
@Transactional
public class CreditHolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CreditHolderRepository			creditHolderRepository;


	// Constructors -----------------------------------------------------------

	public CreditHolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public CreditHolder findByUserAccount(final UserAccount userAccount) {
		CreditHolder result;
		result = this.creditHolderRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public CreditHolder findByUserAccount(final int userAccountId) {
		CreditHolder result;
		result = this.creditHolderRepository.findByUserAccount(userAccountId);
		return result;
	}

	public CreditHolder findByPrincipal() {
		CreditHolder result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Collection<CreditHolder> findAll() {
		Collection<CreditHolder> result;

		result = this.creditHolderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditHolder findOne(final int creditHolderId) {
		Assert.isTrue(creditHolderId != 0);

		CreditHolder result;

		result = this.creditHolderRepository.findOne(creditHolderId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(final CreditHolder creditHolder) {
		Assert.notNull(creditHolder);

		UserAccount result;

		result = creditHolder.getUserAccount();

		return result;
	}
}
