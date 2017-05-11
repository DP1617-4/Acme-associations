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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("Select c from User c where c.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	@Query("Select c from User c where c.relationshipType = ?1")
	Collection<User> findByRelationshipType(String relationshipType);

	@Query("Select c from User c where floor(datediff(Current_date, c.birthDate)/365) = ?1")
	Collection<User> findByAge(Integer age);

	@Query("Select c from User c where (?1 = '' OR ?1=null OR c.relationshipType = ?1) AND (?2 = '' OR ?2=null OR c.genre = ?2) AND (?3 = '' OR ?3=null OR c.country like %?3%) AND (?4 = '' OR ?4=null OR c.state like %?4%) AND (?5 = '' OR ?5=null OR c.province like %?5%) AND (?6 = '' OR ?6=null OR c.city like %?6%) AND (?7=null OR floor(datediff(Current_date, c.birthDate)/365) = ?7) AND (?8 = '' OR ?8=null OR c.name like %?8% OR c.surname like %?8% OR c.description like %?8%)")
	Collection<User> search(String relationshipType, String genre, String country, String state, String province, String city, Integer age, String keyword);

	@Query("Select c from User c where c.name like %?1% OR c.surname like %?1% OR c.description like %?1%")
	Collection<User> findByKeyword(String keyword);

	@Query("select st.useres from SearchTemplate st where st.id = ?1")
	Collection<User> findAllFound(int id);

	@Query("Select c from User c where c.country = ?1")
	Collection<User> findByCountry(String country);

	@Query("Select c from User c where c.genre = ?1")
	Collection<User> findByGenre(String genre);

	@Query("Select c from User c where c.state = ?1")
	Collection<User> findByState(String state);

	@Query("Select c from User c where c.province = ?1")
	Collection<User> findByProvince(String province);

	@Query("Select c from User c where c.city = ?1")
	Collection<User> findByCity(String city);

	@Query("Select s from SearchTemplate s where s.user = ?1")
	SearchTemplate findSearchTemplateByUser(User user);

	@Query("Select c from User c where c.banned = false")
	Collection<User> findAllNotBanned();

	@Query("select l.liked from Likes l where l.user.id = ?1")
	Collection<User> findAllLiked(int userId);

	//Dashboard queries

	@Query("select new list(count(c) as count, c.city as city) from User c group by c.city")
	List<Object[]> useresPerCity();

	@Query("select new list(count(c) as count, c.country as city) from User c group by c.country")
	List<Object[]> useresPerCountry();

	@Query("select sum(floor(datediff(Current_date, c.birthDate)/365))*1.0/(select count(c)*1.0 from User c) from User c")
	Double findAvgUseresAge();

	@Query("select floor(datediff(Current_date, c.birthDate)/365) as result from User c order by result asc")
	List<Integer> findListAgesOrderAsc();

	@Query("select count(c)*1.0/(select count(c)*1.0 from User c) from User c where c.relationshipType = 'ACTIVITIES'")
	Double ratioUserActivities();

	@Query("select count(c)*1.0/(select count(c)*1.0 from User c) from User c where c.relationshipType = 'LOVE'")
	Double ratioUserLove();

	@Query("select count(c)*1.0/(select count(c)*1.0 from User c) from User c where c.relationshipType = 'FRIENDSHIP'")
	Double ratioUserFriendship();

	@Query("select c from Likes l right join l.liked c group by c order by count(l) ASC")
	Collection<User> findUseresOrderByLikes();

	@Query("select ch.sender from Chirp ch group by ch.sender order by count(ch) DESC")
	Collection<User> findUseresMoreChirpsSent();

	@Query("select ch.sender from Chirp ch group by ch.recipient order by count(ch) DESC")
	Collection<User> findUseresMoreChirpsReceived();

	@Query("select l.user from Likes l where l.liked.id=?1")
	Collection<User> findAllLiking(int userID);

	// User 2.0

}
