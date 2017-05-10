/*
 * CreditHolderRepository.java
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

import domain.CreditHolder;

@Repository
public interface CreditHolderRepository extends JpaRepository<CreditHolder, Integer> {

	@Query("select ch from CreditHolder ch where ch.userAccount.id = ?1")
	CreditHolder findByUserAccount(int id);

	@Query("select ch from CreditHolder ch where ch.id = ?1")
	CreditHolder findOne(int id);

}
