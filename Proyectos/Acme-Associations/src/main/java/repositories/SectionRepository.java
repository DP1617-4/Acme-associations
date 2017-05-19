
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;
import domain.User;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

	@Query("select s from Section s where s.association.id = ?1")
	Collection<Section> findAllByAssociation(int associationId);

	@Query("select s.user from Section s where s.id = ?1")
	User findResponsible(int sectionId);

}
