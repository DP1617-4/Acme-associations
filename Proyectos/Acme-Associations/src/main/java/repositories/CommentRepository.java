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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select l from Comment l where l.user.id = ?1")
	Collection<Comment> findAllByUserId(int userId);

	@Query("select l from Comment l where l.user.id = ?1 and l.liked.id = ?2")
	Comment findOneByUserAndLiked(int userId, int likedId);

}