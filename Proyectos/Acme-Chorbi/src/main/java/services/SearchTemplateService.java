
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchTemplateRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.CreditCard;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private SystemConfigurationService	scService;

	@Autowired
	private Validator					validator;


	// Constructors -----------------------------------------------------------

	public SearchTemplateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SearchTemplate create() {

		SearchTemplate created;
		final Collection<Chorbi> chorbies = new ArrayList<Chorbi>();
		created = new SearchTemplate();
		final Chorbi principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);
		created.setChorbi(principal);

		Date now;
		now = new Date(System.currentTimeMillis() - 1);
		created.setMoment(now);
		created.setChorbies(chorbies);

		return created;
	}

	public Collection<SearchTemplate> findAll() {
		Collection<SearchTemplate> result;

		result = this.searchTemplateRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SearchTemplate findOne(final int searchTemplateId) {
		Assert.isTrue(searchTemplateId != 0);

		SearchTemplate result;

		result = this.searchTemplateRepository.findOne(searchTemplateId);
		Assert.notNull(result);

		return result;
	}

	public SearchTemplate save(final SearchTemplate searchTemplate) {

		SearchTemplate saved;
		Assert.notNull(searchTemplate);
		Assert.isTrue(this.checkPrincipal(searchTemplate));
		final CreditCard creditCard = this.creditCardService.findByPrincipal();
		Assert.notNull(creditCard);
		Assert.isTrue(this.creditCardService.checkCCNumber(creditCard.getCreditCardNumber()) && this.creditCardService.expirationDate(creditCard));
		Collection<Chorbi> filtered;

		filtered = this.chorbiService.search(searchTemplate.getRelationshipType(), searchTemplate.getGenre(), searchTemplate.getCountry(), searchTemplate.getState(), searchTemplate.getProvince(), searchTemplate.getCity(), searchTemplate.getAge(),
			searchTemplate.getKeyword());

		searchTemplate.setChorbies(filtered);

		final Date lastUpdate = new Date(System.currentTimeMillis() - 1);
		searchTemplate.setMoment(lastUpdate);

		saved = this.searchTemplateRepository.save(searchTemplate);
		return saved;
	}

	private boolean checkPrincipal(final SearchTemplate searchTemplate) {

		Boolean result = false;
		final UserAccount chorbi = searchTemplate.getChorbi().getUserAccount();
		final UserAccount principal = LoginService.getPrincipal();
		if (chorbi.equals(principal))
			result = true;
		return result;
	}

	public SearchTemplate reconstruct(final SearchTemplate searchTemplate, final BindingResult binding) {
		SearchTemplate result;
		final Chorbi principal = this.chorbiService.findByPrincipal();
		if (searchTemplate.getId() == 0) {
			result = searchTemplate;
			result.setChorbi(principal);
		} else {
			result = this.searchTemplateRepository.findOne(searchTemplate.getId());

			result.setAge(searchTemplate.getAge());
			result.setKeyword(searchTemplate.getKeyword());
			result.setCity(searchTemplate.getCity());
			result.setCountry(searchTemplate.getCountry());
			result.setGenre(searchTemplate.getGenre());
			result.setProvince(searchTemplate.getProvince());
			result.setRelationshipType(searchTemplate.getRelationshipType());
			result.setState(searchTemplate.getState());

			this.validator.validate(result, binding);

		}

		return result;
	}

	public Boolean checkCache(final SearchTemplate searchTemplate) {

		Boolean res = true;

		final Chorbi principal = this.chorbiService.findByPrincipal();
		final SearchTemplate chorbiTemplate = this.findSearchTemplateByChorbi(principal);
		if (chorbiTemplate != null) {
			Boolean relationshipType;
			Boolean age;
			Boolean genre;
			Boolean keyword;
			Boolean country;
			Boolean state;
			Boolean province;
			Boolean city;

			relationshipType = true;
			age = true;
			genre = true;
			keyword = true;
			country = true;
			state = true;
			province = true;
			city = true;

			if (searchTemplate.getRelationshipType() != null)
				relationshipType = searchTemplate.getRelationshipType().equals(searchTemplate.getRelationshipType());
			else if (chorbiTemplate.getRelationshipType() != null)
				relationshipType = chorbiTemplate.getRelationshipType().equals(searchTemplate.getRelationshipType());

			if (searchTemplate.getAge() != null)
				age = searchTemplate.getAge().equals(searchTemplate.getAge());
			else if (chorbiTemplate.getAge() != null)
				age = chorbiTemplate.getAge().equals(searchTemplate.getAge());

			if (searchTemplate.getGenre() != null)
				genre = searchTemplate.getGenre().equals(searchTemplate.getGenre());
			else if (chorbiTemplate.getGenre() != null)
				genre = chorbiTemplate.getGenre().equals(searchTemplate.getGenre());

			if (searchTemplate.getKeyword() != null)
				keyword = searchTemplate.getKeyword().equals(searchTemplate.getKeyword());
			else if (chorbiTemplate.getKeyword() != null)
				keyword = chorbiTemplate.getKeyword().equals(searchTemplate.getKeyword());

			if (searchTemplate.getCountry() != null)
				country = searchTemplate.getCountry().equals(searchTemplate.getCountry());
			else if (chorbiTemplate.getCountry() != null)
				country = chorbiTemplate.getCountry().equals(searchTemplate.getCountry());

			if (searchTemplate.getState() != null)
				state = searchTemplate.getState().equals(searchTemplate.getState());
			else if (chorbiTemplate.getState() != null)
				state = chorbiTemplate.getState().equals(searchTemplate.getState());

			if (searchTemplate.getProvince() != null)
				province = searchTemplate.getProvince().equals(searchTemplate.getProvince());
			else if (chorbiTemplate.getProvince() != null)
				province = chorbiTemplate.getProvince().equals(searchTemplate.getProvince());

			if (searchTemplate.getCity() != null)
				city = searchTemplate.getCity().equals(searchTemplate.getCity());
			else if (chorbiTemplate.getCity() != null)
				city = chorbiTemplate.getCity().equals(searchTemplate.getCity());

			res = relationshipType && age && genre && keyword && country && state && province && city;

		} else
			res = false;

		return res;
	}

	public SearchTemplate findSearchTemplateByChorbi(final Chorbi principal) {
		final SearchTemplate result = this.searchTemplateRepository.findByChorbiId(principal.getId());
		return result;
	}

	public void flush() {
		this.searchTemplateRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
