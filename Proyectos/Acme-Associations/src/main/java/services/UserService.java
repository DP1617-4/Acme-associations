
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import domain.Association;
import domain.Item;
import domain.User;
import forms.RegisterUser;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private Validator		validator;


	public UserService() {
		super();
	}

	public User create() {
		User result;

		result = new User();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		authority.setAuthority(Authority.USER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setUserAccount(userAccount);

		return result;
	}

	public User save(final User user) {
		Assert.notNull(user);

		User result;

		result = this.userRepository.save(user);

		return result;
	}

	public User findOne(final int userId) {
		Assert.isTrue(userId != 0);
		User user;
		user = this.userRepository.findOne(userId);
		Assert.notNull(user);
		return user;
	}

	public User findOneToEdit(final int userId) {
		Assert.isTrue(userId != 0);
		Assert.isTrue(this.checkPrincipal(userId));
		User user;
		user = this.userRepository.findOne(userId);
		return user;
	}

	//	public Collection<User> findAllByAssociation(final Association association, final Pageable pageRequest) {
	//
	//		Collection<User> result;
	//		result = this.userRepository.findAllByAssociation(association.getId(), pageRequest);
	//		return result;
	//
	//	}

	public Collection<User> findAllByAssociation(final Association association) {

		Collection<User> result;
		result = this.userRepository.findAllByAssociation(association.getId());
		return result;

	}

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.userRepository.findAll();
		return result;
	}

	public User reconstruct(final RegisterUser user, final BindingResult binding) {
		User result;
		Assert.isTrue(user.isAccept());
		result = this.create();

		result.setEmail(user.getEmail());
		result.setIdNumber(user.getIdNumber());
		result.setName(user.getName());
		result.setPhoneNumber(user.getPhoneNumber());
		result.setPostalAddress(user.getPostalAddress());
		result.setSurname(user.getSurname());

		result.getUserAccount().setUsername(user.getUsername());
		result.getUserAccount().setPassword(user.getPassword());

		this.validator.validate(result, binding);

		return result;
	}

	public User reconstructPrincipal(final User user, final BindingResult binding) {
		User result;
		result = this.findByPrincipal();

		result.setEmail(user.getEmail());
		result.setIdNumber(user.getIdNumber());
		result.setName(user.getName());
		result.setPhoneNumber(user.getPhoneNumber());
		result.setPostalAddress(user.getPostalAddress());
		result.setSurname(user.getSurname());

		this.validator.validate(result, binding);

		return result;
	}

	public User findByPrincipal() {
		User result;
		result = this.userRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public boolean checkPrincipal(final int userId) {
		User result;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		return result.getId() == userId;
	}

	public User register(final User user) {
		if (user.getId() != 0)
			Assert.isTrue(this.findByPrincipal().getId() == user.getId());
		User result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(user.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		user.getUserAccount().setPassword(pass);

		result = this.userRepository.save(user);
		this.folderService.initFolders(result);
		return result;
	}

	public void flush() {
		this.userRepository.flush();
	}

	public void phoneValidator(final String phoneNumber) {

		//returns exception if user number is not valid.

		final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		String number;
		String cCode;
		int code;
		int num;
		final String[] phone = phoneNumber.split(" ", 2);

		number = phone[1];
		cCode = phone[0];
		cCode = cCode.replaceAll("\\+", "");
		code = Integer.parseInt(cCode);
		num = Integer.parseInt(number);

		PhoneNumber checkNum;

		checkNum = new PhoneNumber();
		checkNum.setCountryCode(code);
		checkNum.setNationalNumber(num);

		Assert.isTrue(phoneUtil.isValidNumber(checkNum));

	}

	public User findAssociationManager(final Association association) {

		return this.userRepository.findAssociationManager(association.getId());
	}

	public Collection<User> findAssociationCollaborators(final Association association) {

		return this.userRepository.findAssociationCollaborators(association.getId());
	}

	public Collection<User> findAssociationCollaboratorsAndManager(final Association association) {

		final Collection<User> result = new ArrayList<User>();
		result.add(this.findAssociationManager(association));
		result.addAll(this.findAssociationCollaborators(association));

		return result;
	}

	public Collection<User> findAllRelatedItem(final Item item) {

		return this.userRepository.findAllRelatedItem(item.getId());
	}

	public Object[] minMaxAvgMembers() {
		final Object[] result = new Object[3];

		result[0] = this.userRepository.avgMembers();
		final List<Long> counts = this.userRepository.findCountMembers();
		result[1] = counts.get(0);
		result[2] = counts.get(counts.size() - 1);

		return result;
	}

	public Collection<User> mostSanctionedUsers() {
		Collection<User> result;

		result = this.userRepository.mostSanctionedUsers();

		return result;
	}

	public User findCollaboratorLeastLoans(final Association association) {

		List<User> users;
		Collection<User> usersAux;
		User user = null;

		usersAux = this.findAssociationCollaborators(association);
		users = new ArrayList<User>(this.userRepository.findCollaboratorLeastLoans(association.getId()));
		if (users.size() > 0)
			user = users.get(0);
		for (final User u : usersAux)
			if (!(users.contains(u))) {

				user = u;
				break;
			}

		return user;

	}

	public User findCollaboratorMostLoans(final Association association) {

		User user = null;
		List<User> users = new ArrayList<User>(this.userRepository.findCollaboratorMostLoans(association.getId()));
		if (!users.isEmpty())
			user = users.get(0);

		return user;
	}

	public User selectUserWithMostSanctionsByAssociation(final Association association) {

		User user = null;
		List<User> users = new ArrayList<User>(this.userRepository.selectUserWithMostSanctionsByAssociation(association.getId()));
		if (!users.isEmpty())
			user = users.get(0);

		return user;
	}

	public Integer countLoansCollaborator(User user, Association association) {

		return this.userRepository.countLoansCollaborator(user.getId(), association.getId());
	}
}
