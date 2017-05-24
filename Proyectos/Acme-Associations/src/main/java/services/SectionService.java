
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Association;
import domain.Section;
import domain.User;

@Service
@Transactional
public class SectionService {

	public SectionService() {
		super();
	}


	// Repository

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private RolesService		roleService;

	@Autowired
	private UserService			userService;


	// Auxiliary services

	public Section create(final Association association) {
		Section result;

		result = new Section();
		result.setAssociation(association);
		return result;
	}

	public Collection<Section> findAllByAssociation(final int associationId) {
		Collection<Section> result;
		result = this.sectionRepository.findAllByAssociation(associationId);
		return result;
	}

	public Section findOne(final int sectionId) {
		Section result;

		result = this.sectionRepository.findOne(sectionId);

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();

		return result;
	}

	public void delete(final Section section) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		this.roleService.checkManager(principal, section.getAssociation());

		this.sectionRepository.delete(section);
	}
	public Section save(final Section section) {
		final User principal = this.userService.findByPrincipal();
		Association association;
		association = section.getAssociation();
		this.roleService.checkManager(principal, association);
		final Section result = this.sectionRepository.save(section);

		return result;
	}

	public Section reconstruct() {
		final Section result = new Section();

		return result;
	}

	public void checkResponsible(final User user, final Section section) {
		Boolean checker = false;
		checker = this.sectionRepository.findResponsible(section.getId()) == user;
		Assert.isTrue(checker, "section.responsible.error");

	}

	public void checkResponsiblePrincipal(final int sectionId) {
		User user;
		user = this.userService.findByPrincipal();
		Boolean checker = false;
		checker = this.sectionRepository.findResponsible(sectionId) == user;
		Assert.isTrue(checker, "section.responsible.error");

	}

	public Section findSectionWithMostLoansByAssociation(Association association) {

		Section section = null;
		List<Section> sections = new ArrayList<Section>(this.sectionRepository.findSectionWithMostLoansByAssociation(association.getId()));
		if (!sections.isEmpty())
			section = sections.get(0);

		return section;
	}

	public Integer countSanctionsByUserAssociation(Section section) {

		return this.sectionRepository.countSanctionsByUserAssociation(section.getId());
	}

}
