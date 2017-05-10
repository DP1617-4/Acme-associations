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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("select st from SearchTemplate st where st.chorbi.id = ?1")
	SearchTemplate findByChorbiId(int chorbiId);

}
