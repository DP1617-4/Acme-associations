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
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select c from Message c where c.folder.id = ?1")
	Collection<Message> findByFolderId(int folderId);

	@Query("select c from Message c where c.recipient.id = ?1")
	Collection<Message> findAllByChorbiId(int chorbiId);

}
