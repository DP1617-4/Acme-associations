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

import domain.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

	@Query("select l from Likes l where l.chorbi.id = ?1")
	Collection<Likes> findAllByChorbiId(int chorbiId);

	@Query("select l from Likes l where l.chorbi.id = ?1 and l.liked.id = ?2")
	Likes findOneByChorbiAndLiked(int chorbiId, int likedId);

}
