
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentRepository;
import domain.Comment;
import domain.Commentable;
import domain.User;

@Service
public class CommentService {

	//managed repository-------------------
	@Autowired
	private CommentRepository	commentRepository;

	//supporting services-------------------
	@Autowired
	private UserService			userService;


	//Basic CRUD methods-------------------

	public Comment create(final Commentable commentable) {

		Comment created;
		created = new Comment();
		final Date moment = new Date(System.currentTimeMillis() - 100);
		final User principal = this.userService.findByPrincipal();
		created.setUser(principal);
		created.setMoment(moment);
		created.setCommentable(commentable);
		return created;
	}

	public Comment findOne(final int commentId) {

		Comment retrieved;
		retrieved = this.commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAllByCommentableId(final int commentableId) {

		Collection<Comment> result;
		result = this.commentRepository.findAllByCommentableId(commentableId);
		return result;
	}

	public Collection<Comment> findAll() {

		return this.commentRepository.findAll();
	}

	public Comment save(final Comment comment) {
		Comment saved;
		final Date moment = new Date(System.currentTimeMillis() - 100);
		comment.setMoment(moment);
		saved = this.commentRepository.save(comment);
		return saved;

	}

	//Auxiliary methods

	//Our other bussiness methods

	public void flush() {
		this.commentRepository.flush();

	}

	public Collection<Comment> findAllByPrincipal() {
		Collection<Comment> result;
		final User user = this.userService.findByPrincipal();
		result = this.commentRepository.findAllByUserId(user.getId());
		return result;
	}

}
