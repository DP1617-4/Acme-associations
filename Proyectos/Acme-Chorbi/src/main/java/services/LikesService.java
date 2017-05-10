
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LikesRepository;
import domain.Chorbi;
import domain.Likes;

@Service
public class LikesService {

	//managed repository-------------------
	@Autowired
	private LikesRepository	likesRepository;

	//supporting services-------------------
	@Autowired
	private ChorbiService	chorbiService;


	//Basic CRUD methods-------------------

	public Likes create(final Chorbi chorbi) {

		Likes created;
		created = new Likes();
		final Date moment = new Date(System.currentTimeMillis() - 100);
		final Chorbi principal = this.chorbiService.findByPrincipal();
		created.setChorbi(principal);
		created.setMoment(moment);
		created.setLiked(chorbi);
		return created;
	}

	public Likes findOne(final int likesId) {

		Likes retrieved;
		retrieved = this.likesRepository.findOne(likesId);
		return retrieved;
	}

	public Likes findByOneToEdit(final int likesId) {
		Likes retrieved;
		retrieved = this.likesRepository.findOne(likesId);
		Assert.isTrue(retrieved.getChorbi().getId() == this.chorbiService.findByPrincipal().getId());
		return retrieved;
	}

	public Collection<Likes> findAllByLikesableId(final int chorbiId) {

		Collection<Likes> result;
		result = this.likesRepository.findAllByChorbiId(chorbiId);
		return result;
	}

	public Collection<Likes> findAll() {

		return this.likesRepository.findAll();
	}

	public Likes findOneByChorbiAndLiked(final int chorbiId, final int likedId) {

		return this.likesRepository.findOneByChorbiAndLiked(chorbiId, likedId);
	}
	public Likes save(final Likes comment) {
		Assert.isTrue(!comment.getLiked().equals(comment.getChorbi()));
		Likes saved;
		final Date moment = new Date(System.currentTimeMillis() - 100);
		comment.setMoment(moment);
		saved = this.likesRepository.save(comment);
		return saved;

	}

	public void delete(final Likes comment) {
		Assert.isTrue(comment.getChorbi().equals(this.chorbiService.findByPrincipal()));
		this.likesRepository.delete(comment);

	}

	//Auxiliary methods

	//Our other bussiness methods

	public void flush() {
		this.likesRepository.flush();

	}

	public Collection<Likes> findAllByPrincipal() {
		Collection<Likes> result;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		result = this.likesRepository.findAllByChorbiId(chorbi.getId());
		return result;
	}

}
