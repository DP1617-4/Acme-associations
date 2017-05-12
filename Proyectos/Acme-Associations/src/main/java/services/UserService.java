
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

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Association;
import domain.User;
import forms.RegisterUser;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository				userRepository;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private FolderService				folderService;

	@Autowired
	private AssociationService			associationService;

	@Autowired
	private Validator					validator;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


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
		userAccount.setEnabled(true);

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

	public User reconstruct(final RegisterUser registerUser, final BindingResult binding) {
		User result;
		Assert.isTrue(registerUser.isAccept());
		result = this.create();

		result.setEmail(registerUser.getEmail());
		result.setIdNumber(registerUser.getIdNumber());
		result.setName(registerUser.getName());
		result.setPhoneNumber(registerUser.getPhoneNumber());
		result.setPostalAddress(registerUser.getPostalAddress());
		result.setSurname(registerUser.getSurname());

		result.getUserAccount().setUsername(registerUser.getUsername());
		result.getUserAccount().setPassword(registerUser.getPassword());

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

}
