
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Manager;
import domain.SystemConfiguration;
import forms.RegisterManager;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository			managerRepository;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private Validator					validator;


	public ManagerService() {
		super();
	}

	//Dashboard

	// A listing of managers sorted by the number of events that they organise.
	public Collection<Manager> findManagersOrderByEvent() {
		Collection<Manager> result;
		result = this.managerRepository.findManagersOrderByEvent();
		return result;
	}

	public Manager create() {
		Manager result;

		result = new Manager();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		authority.setAuthority(Authority.MANAGER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		result.setUserAccount(userAccount);

		return result;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		Manager result;

		result = this.managerRepository.save(manager);

		return result;
	}

	public Manager findOne(final int managerId) {
		Assert.isTrue(managerId != 0);
		Manager manager;
		manager = this.managerRepository.findOne(managerId);
		Assert.notNull(manager);
		return manager;
	}

	public Manager findOneToEdit(final int managerId) {
		Assert.isTrue(managerId != 0);
		Assert.isTrue(this.checkPrincipal());
		Manager manager;
		manager = this.managerRepository.findOne(managerId);
		return manager;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;
		result = this.managerRepository.findAll();
		return result;
	}

	public ArrayList<Object> reconstruct(final RegisterManager registerManager, final BindingResult binding) {
		final ArrayList<Object> result = new ArrayList<Object>();
		Manager manager;
		Assert.isTrue(registerManager.isAccept());
		final CreditCard creditCard = new CreditCard();
		manager = this.create();

		manager.setName(registerManager.getName());
		manager.setSurname(registerManager.getSurname());
		manager.setPhoneNumber(registerManager.getPhoneNumber());
		manager.setEmail(registerManager.getEmail());
		manager.setCumulatedFee(this.systemConfigurationService.findMain().getFeeManager());

		manager.setCompanyName(registerManager.getCompanyName());
		manager.setVATNumber(registerManager.getVATNumber());

		manager.getUserAccount().setUsername(registerManager.getUsername());

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String password = registerManager.getPassword();
		String hash;
		hash = encoder.encodePassword(password, null);

		manager.getUserAccount().setPassword(hash);

		creditCard.setBrandName(registerManager.getBrandName());
		creditCard.setHolderName(registerManager.getHolderName());
		creditCard.setCreditCardNumber(registerManager.getCreditCardNumber());
		creditCard.setCVV(registerManager.getCVV());
		creditCard.setExpirationMonth(registerManager.getExpirationMonth());
		creditCard.setExpirationYear(registerManager.getExpirationYear());

		result.add(manager);
		result.add(creditCard);

		return result;
	}
	public Manager reconstruct(final Manager manager, final BindingResult binding) {
		Manager result;

		if (manager.getId() == 0)
			result = manager;
		else {
			result = this.managerRepository.findOne(manager.getId());

			result.setName(manager.getName());
			result.setSurname(manager.getSurname());
			result.setPhoneNumber(manager.getPhoneNumber());
			result.setEmail(manager.getEmail());

			result.setCompanyName(manager.getCompanyName());
			result.setVATNumber(manager.getVATNumber());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Manager findByPrincipal() {
		Manager result;
		result = this.managerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public boolean checkPrincipal() {
		Manager result;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.managerRepository.findByUserAccountId(userAccount.getId());
		return result != null;
	}

	public Manager register(final Manager manager) {
		if (manager.getId() != 0)
			Assert.isTrue(this.findByPrincipal().getId() == manager.getId());
		Manager result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(manager.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		manager.getUserAccount().setPassword(pass);

		result = this.managerRepository.save(manager);
		return result;
	}

	public Collection<Manager> findLikersOfManager(final int likedId) {
		final Collection<Manager> result = this.findLikersOfManager(likedId);

		return result;
	}

	public void updateFee(Manager manager) {
		final SystemConfiguration sc = this.systemConfigurationService.findMain();
		manager.setCumulatedFee(manager.getCumulatedFee() + sc.getFeeManager());
		manager = this.save(manager);
	}

	public void flush() {
		this.managerRepository.flush();
	}

}
