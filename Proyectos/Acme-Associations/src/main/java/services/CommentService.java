
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Association;
import domain.Comment;
import domain.Commentable;
import domain.Item;
import domain.Meeting;
import domain.Minutes;
import domain.User;

@Service
public class CommentService {

	//managed repository-------------------
	@Autowired
	private CommentRepository	commentRepository;

	//supporting services-------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private Validator			validator;


	//Basic CRUD methods-------------------

	public Comment create(final int commentableId) {

		Comment created;
		created = new Comment();
		Commentable commentable;

		commentable = this.commentableService.findOne(commentableId);
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

	public Comment reconstruct(final Comment comment, final BindingResult binding) {

		final Date moment = new Date(System.currentTimeMillis() - 100);
		final User principal = this.userService.findByPrincipal();
		comment.setUser(principal);
		comment.setMoment(moment);

		this.validator.validate(comment, binding);
		return comment;
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

	public void checkPrincipalCanComment(final Commentable commentable) {

		User principal;

		principal = this.userService.findByPrincipal();

		if (commentable instanceof Association)
			Assert.isTrue(this.userService.findAllByAssociation((Association) commentable).contains(principal), "commentable.association.error");
		if (commentable instanceof Item)
			Assert.isTrue(this.userService.findAllRelatedItem((Item) commentable).contains(principal), "commentable.item.error");
		final Meeting meeting;
		if (commentable instanceof Meeting) {
			meeting = (Meeting) commentable;
			Assert.isTrue(this.userService.findAllByAssociation(meeting.getAssociation()).contains(principal), "commentable.association.error");
		}
		Minutes minutes;
		if (commentable instanceof Minutes) {
			minutes = (Minutes) commentable;
			Assert.isTrue(minutes.getUsers().contains(principal), "commentable.minutes.error");
		}
	}
}
