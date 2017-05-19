
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.section.association.id=?1 and (i.itemCondition = 'EXCELENT' or i.itemCondition = 'MODERATE' or i.itemCondition = 'GOOD')")
	Collection<Item> findAllByAssociation(int associationId);

	@Query("select i from Item i where i.section.id=?1")
	Collection<Item> findAllBySection(int sectionId);

}
