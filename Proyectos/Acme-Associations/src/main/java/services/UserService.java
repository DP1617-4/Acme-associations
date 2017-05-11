
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.SystemConfiguration;
import domain.User;

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

		final DateTime date = new DateTime().minusYears(18);
		final DateTime birth = new DateTime(user.getBirthDate());
		Assert.isTrue(date.isAfter(birth) || date.isEqual(birth), "Dear user, you must be over 18 to register");
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

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.userRepository.findAll();
		return result;
	}

	public Collection<User> findAllNotBanned() {
		Collection<User> result;
		result = this.userRepository.findAllNotBanned();
		return result;
	}

	public User reconstruct(final RegisterUser registerUser, final BindingResult binding) {
		User result;
		Assert.isTrue(registerUser.isAccept());
		result = this.create();

		result.setBirthDate(registerUser.getBirthDate());
		result.setCity(registerUser.getCity());
		result.setCountry(registerUser.getCountry());
		result.setDescription(registerUser.getDescription());
		result.setEmail(registerUser.getEmail());
		result.setGenre(registerUser.getGenre());
		result.setName(registerUser.getName());
		result.setPhoneNumber(registerUser.getPhoneNumber());
		result.setPicture(registerUser.getPicture());
		result.setProvince(registerUser.getProvince());
		result.setRelationshipType(registerUser.getRelationshipType());
		result.setState(registerUser.getState());
		result.setSurname(registerUser.getSurname());

		result.getUserAccount().setUsername(registerUser.getUsername());
		result.getUserAccount().setPassword(registerUser.getPassword());

		return result;
	}

	public User reconstruct(final User user, final BindingResult binding) {
		User result;

		if (user.getId() == 0)
			result = user;
		else {
			result = this.userRepository.findOne(user.getId());

			result.setBirthDate(user.getBirthDate());
			result.setCity(user.getCity());
			result.setCountry(user.getCountry());
			result.setDescription(user.getDescription());
			result.setEmail(user.getEmail());
			result.setGenre(user.getGenre());
			result.setName(user.getName());
			result.setPhoneNumber(user.getPhoneNumber());
			result.setPicture(user.getPicture());
			result.setProvince(user.getProvince());
			result.setRelationshipType(user.getRelationshipType());
			result.setState(user.getState());
			result.setSurname(user.getSurname());

			this.validator.validate(result, binding);
		}

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

	public void banUser(final int userId) {
		this.administratorService.checkAdministrator();
		User user;
		user = this.userRepository.findOne(userId);
		if (user.getBanned()) {
			user.setBanned(false);
			user.getUserAccount().setEnabled(true);
		} else {
			user.setBanned(true);
			user.getUserAccount().setEnabled(false);
		}
		this.userRepository.save(user);
	}

	public Collection<User> findLikersOfUser(final int likedId) {
		final Collection<User> result = this.findLikersOfUser(likedId);

		return result;
	}

	public Collection<User> findByRelationshipType(final String relationshipType) {
		return this.userRepository.findByRelationshipType(relationshipType);
	}

	public Collection<User> findByAge(final Integer age) {
		return this.userRepository.findByAge(age);
	}

	public Collection<User> findByKeyword(final String keyword) {
		return this.userRepository.findByKeyword(keyword);
	}

	public Collection<User> findByCountry(final String country) {
		return this.userRepository.findByCountry(country);
	}

	public Collection<User> findByGenre(final String genre) {
		return this.userRepository.findByGenre(genre);
	}

	public Collection<User> findByState(final String state) {
		return this.userRepository.findByState(state);
	}

	public Collection<User> findByProvince(final String province) {
		return this.userRepository.findByProvince(province);
	}

	public Collection<User> findByCity(final String city) {
		return this.userRepository.findByCity(city);
	}

	public void flush() {
		this.userRepository.flush();
	}

	public Collection<User> findAllFound(final int searchTemplateId) {

		Collection<User> filtered;
		filtered = this.userRepository.findAllFound(searchTemplateId);

		return filtered;
	}

	public void sumFee(final User user) {
		final SystemConfiguration sc = this.systemConfigurationService.findMain();
		user.setCumulatedFee(user.getCumulatedFee() + sc.getFeeUser());
		this.save(user);
	}

	//Dashboard methods
	// A listing with the number of useres per country and city.
	public List<Object[]> useresPerCity() {
		final List<Object[]> result = this.userRepository.useresPerCity();

		return result;
	}

	public List<Object[]> useresPerCountry() {
		final List<Object[]> result = this.userRepository.useresPerCountry();
		return result;
	}

	//The minimum, the maximum, and the average ages of the useres
	public Double findAvgUseresAge() {
		final Double result = this.userRepository.findAvgUseresAge();
		return result;
	}

	public Integer[] findMinMaxUseresAge() {
		final List<Integer> doubles = this.userRepository.findListAgesOrderAsc();
		final Collection<Integer> result = new ArrayList<Integer>();
		result.add(doubles.get(0));
		result.add(doubles.get(doubles.size() - 1));
		return result.toArray(new Integer[0]);
	}

	//The ratios of useres who search for "activities", "friendship", and "love".
	public Double ratioUserActivities() {
		final Double result = this.userRepository.ratioUserActivities();
		return result;
	}

	public Double ratioUserFriendship() {
		final Double result = this.userRepository.ratioUserFriendship();
		return result;
	}

	public Double ratioUserLove() {
		final Double result = this.userRepository.ratioUserLove();
		return result;
	}

	// The list of useres, sorted by the number of likes they have got.
	public Collection<User> findUseresOrderByLikes() {
		final Collection<User> result = this.userRepository.findUseresOrderByLikes();
		return result;
	}

	//Coger solo los dos primeros en estas dos
	public Collection<User> findUseresMoreChirpsSent() {
		final Collection<User> useres = this.userRepository.findUseresMoreChirpsSent();
		if (useres.size() <= 3)
			return useres;
		else {
			final List<User> result = new ArrayList<User>(useres);
			return result.subList(0, 2);
		}
	}

	public Collection<User> findUseresMoreChirpsReceived() {
		final Collection<User> useres = this.userRepository.findUseresMoreChirpsReceived();
		if (useres.size() <= 3)
			return useres;
		else {
			final List<User> result = new ArrayList<User>(useres);
			return result.subList(0, 2);
		}
	}

	public Collection<User> findAllLikingMe(final int userId) {

		Collection<User> result;
		Assert.isTrue(this.userRepository.exists(userId));
		result = this.userRepository.findAllLiking(userId);
		return result;
	}

	public Collection<User> findAllLiked(final int userId) {
		Collection<User> result;
		result = this.userRepository.findAllLiked(userId);
		return result;
	}

	// Dashboard 2.0

	// A listing of useres sorted by the number of events to which they have registered.
	public Collection<User> findUseresOrderedByEvents() {
		Collection<User> result;
		result = this.userRepository.findUseresOrderedByEvents();
		return result;
	}

	public List<User> findUseresRegisteredEvent(final int eventId, final Pageable pageRequest) {

		List<User> result;
		result = this.userRepository.findUseresRegisteredEvent(eventId, pageRequest);

		return result;
	}

	// The list of useres, sorted by the average number of stars that they've got.
	public Collection<User> findUseresOrderedByAvgStars() {
		Collection<User> result;
		result = this.userRepository.findUseresOrderedByAvgStars();
		return result;
	}

	public Collection<User> search(final String relationshipType, final String genre, final String country, final String state, final String province, final String city, final Integer age, final String keyword) {
		Collection<User> found;
		found = this.userRepository.search(relationshipType, genre, country, state, province, city, age, keyword);
		return found;
	}
}
