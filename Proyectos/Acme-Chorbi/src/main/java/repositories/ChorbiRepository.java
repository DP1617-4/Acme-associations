/*
 * ActorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.SearchTemplate;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("Select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	@Query("Select c from Chorbi c where c.relationshipType = ?1")
	Collection<Chorbi> findByRelationshipType(String relationshipType);

	@Query("Select c from Chorbi c where floor(datediff(Current_date, c.birthDate)/365) = ?1")
	Collection<Chorbi> findByAge(Integer age);

	@Query("Select c from Chorbi c where (?1 = '' OR ?1=null OR c.relationshipType = ?1) AND (?2 = '' OR ?2=null OR c.genre = ?2) AND (?3 = '' OR ?3=null OR c.country like %?3%) AND (?4 = '' OR ?4=null OR c.state like %?4%) AND (?5 = '' OR ?5=null OR c.province like %?5%) AND (?6 = '' OR ?6=null OR c.city like %?6%) AND (?7=null OR floor(datediff(Current_date, c.birthDate)/365) = ?7) AND (?8 = '' OR ?8=null OR c.name like %?8% OR c.surname like %?8% OR c.description like %?8%)")
	Collection<Chorbi> search(String relationshipType, String genre, String country, String state, String province, String city, Integer age, String keyword);

	@Query("Select c from Chorbi c where c.name like %?1% OR c.surname like %?1% OR c.description like %?1%")
	Collection<Chorbi> findByKeyword(String keyword);

	@Query("select st.chorbies from SearchTemplate st where st.id = ?1")
	Collection<Chorbi> findAllFound(int id);

	@Query("Select c from Chorbi c where c.country = ?1")
	Collection<Chorbi> findByCountry(String country);

	@Query("Select c from Chorbi c where c.genre = ?1")
	Collection<Chorbi> findByGenre(String genre);

	@Query("Select c from Chorbi c where c.state = ?1")
	Collection<Chorbi> findByState(String state);

	@Query("Select c from Chorbi c where c.province = ?1")
	Collection<Chorbi> findByProvince(String province);

	@Query("Select c from Chorbi c where c.city = ?1")
	Collection<Chorbi> findByCity(String city);

	@Query("Select s from SearchTemplate s where s.chorbi = ?1")
	SearchTemplate findSearchTemplateByChorbi(Chorbi chorbi);

	@Query("Select c from Chorbi c where c.banned = false")
	Collection<Chorbi> findAllNotBanned();

	@Query("select l.liked from Likes l where l.chorbi.id = ?1")
	Collection<Chorbi> findAllLiked(int chorbiId);

	//Dashboard queries

	@Query("select new list(count(c) as count, c.city as city) from Chorbi c group by c.city")
	List<Object[]> chorbiesPerCity();

	@Query("select new list(count(c) as count, c.country as city) from Chorbi c group by c.country")
	List<Object[]> chorbiesPerCountry();

	@Query("select sum(floor(datediff(Current_date, c.birthDate)/365))*1.0/(select count(c)*1.0 from Chorbi c) from Chorbi c")
	Double findAvgChorbiesAge();

	@Query("select floor(datediff(Current_date, c.birthDate)/365) as result from Chorbi c order by result asc")
	List<Integer> findListAgesOrderAsc();

	@Query("select count(c)*1.0/(select count(c)*1.0 from Chorbi c) from Chorbi c where c.relationshipType = 'ACTIVITIES'")
	Double ratioChorbiActivities();

	@Query("select count(c)*1.0/(select count(c)*1.0 from Chorbi c) from Chorbi c where c.relationshipType = 'LOVE'")
	Double ratioChorbiLove();

	@Query("select count(c)*1.0/(select count(c)*1.0 from Chorbi c) from Chorbi c where c.relationshipType = 'FRIENDSHIP'")
	Double ratioChorbiFriendship();

	@Query("select c from Likes l right join l.liked c group by c order by count(l) ASC")
	Collection<Chorbi> findChorbiesOrderByLikes();

	@Query("select ch.sender from Chirp ch group by ch.sender order by count(ch) DESC")
	Collection<Chorbi> findChorbiesMoreChirpsSent();

	@Query("select ch.sender from Chirp ch group by ch.recipient order by count(ch) DESC")
	Collection<Chorbi> findChorbiesMoreChirpsReceived();

	@Query("select l.chorbi from Likes l where l.liked.id=?1")
	Collection<Chorbi> findAllLiking(int chorbiID);

	// Chorbi 2.0

	@Query("select c from Event e right join e.registered c group by c order by count(e) DESC")
	Collection<Chorbi> findChorbiesOrderedByEvents();

	@Query("select e.registered from Event e where e.id=?1")
	List<Chorbi> findChorbiesRegisteredEvent(int eventId, Pageable pageRequest);

	@Query("select l.liked from Likes l group by l.liked order by avg(l.stars) DESC")
	Collection<Chorbi> findChorbiesOrderedByAvgStars();

}
