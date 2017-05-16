
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
public class CommentableService {

	//managed repository-------------------
	@Autowired
	private CommentableRepository	commentableRepository;

	//supporting services-------------------
	@Autowired
	private UserService				userService;


	//Basic CRUD methods-------------------

	public Commentable findOne(final int commentableId) {

		Commentable retrieved;
		retrieved = this.commentableRepository.findOne(commentableId);
		return retrieved;
	}

	public Collection<Commentable> findAll() {

		return this.commentableRepository.findAll();
	}
	//Auxiliary methods

	//Our other bussiness methods

	public void flush() {
		this.commentableRepository.flush();

	}

}
