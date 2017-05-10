
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

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.SystemConfiguration;
import forms.RegisterChorbi;

@Service
@Transactional
public class ChorbiService {

	@Autowired
	private ChorbiRepository			chorbiRepository;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SearchTemplateService		searchTemplateService;

	@Autowired
	private FolderService				folderService;

	@Autowired
	private Validator					validator;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	public ChorbiService() {
		super();
	}

	public Chorbi create() {
		Chorbi result;

		result = new Chorbi();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		authority.setAuthority(Authority.CHORBI);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		result.setBanned(false);
		result.setUserAccount(userAccount);

		return result;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		final DateTime date = new DateTime().minusYears(18);
		final DateTime birth = new DateTime(chorbi.getBirthDate());
		Assert.isTrue(date.isAfter(birth) || date.isEqual(birth), "Dear user, you must be over 18 to register");
		Chorbi result;

		result = this.chorbiRepository.save(chorbi);

		return result;
	}

	public Chorbi findOne(final int chorbiId) {
		Assert.isTrue(chorbiId != 0);
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(chorbiId);
		Assert.notNull(chorbi);
		return chorbi;
	}

	public Chorbi findOneToEdit(final int chorbiId) {
		Assert.isTrue(chorbiId != 0);
		Assert.isTrue(this.checkPrincipal(chorbiId));
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(chorbiId);
		return chorbi;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAll();
		return result;
	}

	public Collection<Chorbi> findAllNotBanned() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAllNotBanned();
		return result;
	}

	public Chorbi reconstruct(final RegisterChorbi registerChorbi, final BindingResult binding) {
		Chorbi result;
		Assert.isTrue(registerChorbi.isAccept());
		result = this.create();

		result.setBirthDate(registerChorbi.getBirthDate());
		result.setCity(registerChorbi.getCity());
		result.setCountry(registerChorbi.getCountry());
		result.setDescription(registerChorbi.getDescription());
		result.setEmail(registerChorbi.getEmail());
		result.setGenre(registerChorbi.getGenre());
		result.setName(registerChorbi.getName());
		result.setPhoneNumber(registerChorbi.getPhoneNumber());
		result.setPicture(registerChorbi.getPicture());
		result.setProvince(registerChorbi.getProvince());
		result.setRelationshipType(registerChorbi.getRelationshipType());
		result.setState(registerChorbi.getState());
		result.setSurname(registerChorbi.getSurname());

		result.getUserAccount().setUsername(registerChorbi.getUsername());
		result.getUserAccount().setPassword(registerChorbi.getPassword());

		return result;
	}

	public Chorbi reconstruct(final Chorbi chorbi, final BindingResult binding) {
		Chorbi result;

		if (chorbi.getId() == 0)
			result = chorbi;
		else {
			result = this.chorbiRepository.findOne(chorbi.getId());

			result.setBirthDate(chorbi.getBirthDate());
			result.setCity(chorbi.getCity());
			result.setCountry(chorbi.getCountry());
			result.setDescription(chorbi.getDescription());
			result.setEmail(chorbi.getEmail());
			result.setGenre(chorbi.getGenre());
			result.setName(chorbi.getName());
			result.setPhoneNumber(chorbi.getPhoneNumber());
			result.setPicture(chorbi.getPicture());
			result.setProvince(chorbi.getProvince());
			result.setRelationshipType(chorbi.getRelationshipType());
			result.setState(chorbi.getState());
			result.setSurname(chorbi.getSurname());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Chorbi findByPrincipal() {
		Chorbi result;
		result = this.chorbiRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public boolean checkPrincipal(final int chorbiId) {
		Chorbi result;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.chorbiRepository.findByUserAccountId(userAccount.getId());
		return result.getId() == chorbiId;
	}

	public Chorbi register(final Chorbi chorbi) {
		if (chorbi.getId() != 0)
			Assert.isTrue(this.findByPrincipal().getId() == chorbi.getId());
		Chorbi result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(chorbi.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		chorbi.getUserAccount().setPassword(pass);

		result = this.chorbiRepository.save(chorbi);
		this.folderService.initFolders(result);
		return result;
	}

	public void banChorbi(final int chorbiId) {
		this.administratorService.checkAdministrator();
		Chorbi chorbi;
		chorbi = this.chorbiRepository.findOne(chorbiId);
		if (chorbi.getBanned()) {
			chorbi.setBanned(false);
			chorbi.getUserAccount().setEnabled(true);
		} else {
			chorbi.setBanned(true);
			chorbi.getUserAccount().setEnabled(false);
		}
		this.chorbiRepository.save(chorbi);
	}

	public Collection<Chorbi> findLikersOfChorbi(final int likedId) {
		final Collection<Chorbi> result = this.findLikersOfChorbi(likedId);

		return result;
	}

	public Collection<Chorbi> findByRelationshipType(final String relationshipType) {
		return this.chorbiRepository.findByRelationshipType(relationshipType);
	}

	public Collection<Chorbi> findByAge(final Integer age) {
		return this.chorbiRepository.findByAge(age);
	}

	public Collection<Chorbi> findByKeyword(final String keyword) {
		return this.chorbiRepository.findByKeyword(keyword);
	}

	public Collection<Chorbi> findByCountry(final String country) {
		return this.chorbiRepository.findByCountry(country);
	}

	public Collection<Chorbi> findByGenre(final String genre) {
		return this.chorbiRepository.findByGenre(genre);
	}

	public Collection<Chorbi> findByState(final String state) {
		return this.chorbiRepository.findByState(state);
	}

	public Collection<Chorbi> findByProvince(final String province) {
		return this.chorbiRepository.findByProvince(province);
	}

	public Collection<Chorbi> findByCity(final String city) {
		return this.chorbiRepository.findByCity(city);
	}

	public void flush() {
		this.chorbiRepository.flush();
	}

	public Collection<Chorbi> findAllFound(final int searchTemplateId) {

		Collection<Chorbi> filtered;
		filtered = this.chorbiRepository.findAllFound(searchTemplateId);

		return filtered;
	}

	public void sumFee(final Chorbi chorbi) {
		final SystemConfiguration sc = this.systemConfigurationService.findMain();
		chorbi.setCumulatedFee(chorbi.getCumulatedFee() + sc.getFeeChorbi());
		this.save(chorbi);
	}

	//Dashboard methods
	// A listing with the number of chorbies per country and city.
	public List<Object[]> chorbiesPerCity() {
		final List<Object[]> result = this.chorbiRepository.chorbiesPerCity();

		return result;
	}

	public List<Object[]> chorbiesPerCountry() {
		final List<Object[]> result = this.chorbiRepository.chorbiesPerCountry();
		return result;
	}

	//The minimum, the maximum, and the average ages of the chorbies
	public Double findAvgChorbiesAge() {
		final Double result = this.chorbiRepository.findAvgChorbiesAge();
		return result;
	}

	public Integer[] findMinMaxChorbiesAge() {
		final List<Integer> doubles = this.chorbiRepository.findListAgesOrderAsc();
		final Collection<Integer> result = new ArrayList<Integer>();
		result.add(doubles.get(0));
		result.add(doubles.get(doubles.size() - 1));
		return result.toArray(new Integer[0]);
	}

	//The ratios of chorbies who search for "activities", "friendship", and "love".
	public Double ratioChorbiActivities() {
		final Double result = this.chorbiRepository.ratioChorbiActivities();
		return result;
	}

	public Double ratioChorbiFriendship() {
		final Double result = this.chorbiRepository.ratioChorbiFriendship();
		return result;
	}

	public Double ratioChorbiLove() {
		final Double result = this.chorbiRepository.ratioChorbiLove();
		return result;
	}

	// The list of chorbies, sorted by the number of likes they have got.
	public Collection<Chorbi> findChorbiesOrderByLikes() {
		final Collection<Chorbi> result = this.chorbiRepository.findChorbiesOrderByLikes();
		return result;
	}

	//Coger solo los dos primeros en estas dos
	public Collection<Chorbi> findChorbiesMoreChirpsSent() {
		final Collection<Chorbi> chorbies = this.chorbiRepository.findChorbiesMoreChirpsSent();
		if (chorbies.size() <= 3)
			return chorbies;
		else {
			final List<Chorbi> result = new ArrayList<Chorbi>(chorbies);
			return result.subList(0, 2);
		}
	}

	public Collection<Chorbi> findChorbiesMoreChirpsReceived() {
		final Collection<Chorbi> chorbies = this.chorbiRepository.findChorbiesMoreChirpsReceived();
		if (chorbies.size() <= 3)
			return chorbies;
		else {
			final List<Chorbi> result = new ArrayList<Chorbi>(chorbies);
			return result.subList(0, 2);
		}
	}

	public Collection<Chorbi> findAllLikingMe(final int chorbiId) {

		Collection<Chorbi> result;
		Assert.isTrue(this.chorbiRepository.exists(chorbiId));
		result = this.chorbiRepository.findAllLiking(chorbiId);
		return result;
	}

	public Collection<Chorbi> findAllLiked(final int chorbiId) {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAllLiked(chorbiId);
		return result;
	}

	// Dashboard 2.0

	// A listing of chorbies sorted by the number of events to which they have registered.
	public Collection<Chorbi> findChorbiesOrderedByEvents() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findChorbiesOrderedByEvents();
		return result;
	}

	public List<Chorbi> findChorbiesRegisteredEvent(final int eventId, final Pageable pageRequest) {

		List<Chorbi> result;
		result = this.chorbiRepository.findChorbiesRegisteredEvent(eventId, pageRequest);

		return result;
	}

	// The list of chorbies, sorted by the average number of stars that they've got.
	public Collection<Chorbi> findChorbiesOrderedByAvgStars() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findChorbiesOrderedByAvgStars();
		return result;
	}

	public Collection<Chorbi> search(final String relationshipType, final String genre, final String country, final String state, final String province, final String city, final Integer age, final String keyword) {
		Collection<Chorbi> found;
		found = this.chorbiRepository.search(relationshipType, genre, country, state, province, city, age, keyword);
		return found;
	}
}
