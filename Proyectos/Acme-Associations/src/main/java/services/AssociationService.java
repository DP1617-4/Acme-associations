
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AssociationRepository;
import domain.Association;

@Service
@Transactional
public class AssociationService {

	//managed repository ---------------------------------------
	@Autowired
	private AssociationRepository	associationRepository;


	//supporting services --------------------------------------

	// Constructors --------------------------------------------
	public AssociationService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Association create() {
		Association created;
		created = new Association();
		return created;
	}

	public Association findOne(final int associationId) {
		Association retrieved;
		retrieved = this.associationRepository.findOne(associationId);
		return retrieved;
	}

	public Collection<Association> findAll() {
		Collection<Association> result;
		result = this.associationRepository.findAll();
		return result;
	}

	public Association save(final Association association) {

	}

	public void delete(final Association association) {
		this.associationRepository.delete(association);
	}

	//Auxiliary methods ----------------------------------------

	//Our other bussiness methods ------------------------------

}
