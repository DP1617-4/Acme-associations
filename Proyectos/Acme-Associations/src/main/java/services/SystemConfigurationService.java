
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	//managed repository---------------------
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	//supporting services -------------------

	@Autowired
	private AdministratorService			adminService;

	@Autowired
	private ChirpService					messageService;


	//Basic CRUD methods --------------------
	public SystemConfiguration create() {
		SystemConfiguration created;
		created = new SystemConfiguration();
		return created;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		this.adminService.checkAdministrator();
		SystemConfiguration retrieved;
		retrieved = this.systemConfigurationRepository.findOne(systemConfigurationId);
		return retrieved;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;
		result = this.systemConfigurationRepository.findAll();
		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		this.adminService.checkAdministrator();
		SystemConfiguration saved;

		Date time1;
		try {
			time1 = new SimpleDateFormat("HH:mm:ss").parse("12:00:00");

			final Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			final Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(systemConfiguration.getCacheTime());

			Assert.isTrue(calendar1.before(calendar2) || calendar1.equals(calendar2));
		} catch (final ParseException e) {

		}
		saved = this.systemConfigurationRepository.save(systemConfiguration);
		return saved;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	//Auxiliary methods ---------------------

	//Our other bussiness methods -----------
	public SystemConfiguration findMain() {
		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.findMain();
		return systemConfiguration;
	}

	public String findRandomBanner() {

		String result;
		SystemConfiguration sc;
		int randomNum;
		final Random rn = new Random();
		List<String> banners;

		sc = this.findMain();
		randomNum = rn.nextInt(sc.getBanners().size());
		banners = new ArrayList<String>(sc.getBanners());

		result = banners.get(randomNum);

		return result;
	}

	public void flush() {
		this.systemConfigurationRepository.flush();
	}

	//Dashboard queries

	//The ratio of chorbies who have not registered a credit card or have regis-tered an invalid credit card.
	public Double findRatioChorbiesWithoutCreditCard() {
		final Double result = this.systemConfigurationRepository.findRatioChorbiesWithoutCreditCard();
		return result;
	}

	//The minimum, the maximum, and the average number of likes per chorbi.
	public Double averageLikesPerChorbi() {
		final Double result = this.systemConfigurationRepository.averageLikesPerChorbi();
		return result;
	}

	public Long[] minMaxLikesPerChorbi() {
		final Collection<Long> result = new ArrayList<Long>();
		final List<Long> doubles = this.systemConfigurationRepository.listNumberLikesPerChorbiASC();
		if (doubles.isEmpty()) {
			result.add((long) 0);
			result.add((long) 0);
		} else {
			result.add(doubles.get(0));
			result.add(doubles.get(doubles.size() - 1));
		}
		return result.toArray(new Long[0]);
	}

	// The minimum, the maximum, and the average number of chirps that a chor-bi receives from other chorbies.
	public Double averageChirpsToChorbi() {
		final Double result = this.systemConfigurationRepository.averageChirpsToChorbi();
		return result;
	}

	public Long[] minMaxChirpsToChorbi() {
		final Collection<Long> result = new ArrayList<Long>();
		final List<Long> doubles = this.systemConfigurationRepository.listNumberChirpsToChorbiASC();
		if (doubles.isEmpty()) {
			result.add((long) 0);
			result.add((long) 0);
		} else {
			result.add(doubles.get(0));
			result.add(doubles.get(doubles.size() - 1));
		}
		return result.toArray(new Long[0]);
	}

	// The minimum, the maximum, and the average number of chirps that a chor-bi sends to other chorbies.
	public Double averageChirpsFromChorbi() {
		final Double result = this.systemConfigurationRepository.averageChirpsFromChorbi();
		return result;
	}

	public Long[] minMaxChirpsFromChorbi() {
		final Collection<Long> result = new ArrayList<Long>();
		final List<Long> doubles = this.systemConfigurationRepository.listNumberChirpsFromChorbiASC();
		if (doubles.isEmpty()) {
			result.add((long) 0);
			result.add((long) 0);
		} else {
			result.add(doubles.get(0));
			result.add(doubles.get(doubles.size() - 1));
		}
		return result.toArray(new Long[0]);
	}

	//Dashboard 2.0

	// The minimum, the maximum, and the average number of stars per chorbi.
	public Object[] minMaxAvgStars() {
		Object[] result;
		result = this.systemConfigurationRepository.minMaxAvgStars();
		return result;
	}

}
