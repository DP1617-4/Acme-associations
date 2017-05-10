
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SystemConfiguration;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Integer> {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findMain();

	//Dashboard

	@Query("select count(cc)*1.0/(select count(c)*1.0 from Chorbi c) from CreditCard cc")
	Double findRatioChorbiesWithoutCreditCard();

	@Query("select count(l)*1.0/(select count(c)*1.0 from Chorbi c) from Likes l")
	Double averageLikesPerChorbi();

	@Query("select count(l) from Likes l right join l.liked c group by c order by count(l) ASC")
	List<Long> listNumberLikesPerChorbiASC();

	@Query("select count(ch)*1.0/(select count(c)*1.0 from Chorbi c) from Chirp ch where ch.folder.name = 'Received'")
	Double averageChirpsToChorbi();

	@Query("select count(ch) from Chirp ch right join ch.recipient c where ch.folder.name = 'Received' group by c order by count(ch) ASC")
	List<Long> listNumberChirpsToChorbiASC();

	@Query("select count(ch)*1.0/(select count(c)*1.0 from Chorbi c) from Chirp ch where ch.folder.name = 'Sent'")
	Double averageChirpsFromChorbi();

	@Query("select count(ch) from Chirp ch right join ch.sender c where ch.folder.name = 'Sent' group by c order by count(ch) ASC")
	List<Long> listNumberChirpsFromChorbiASC();

	//Dashboard 2.0

	@Query("select min(l.stars), avg(l.stars), max(l.stars) from Likes l")
	Object[] minMaxAvgStars();
}
