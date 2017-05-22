
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select l.item from Loan l right JOIN l.item i where l.finalDate!=null and i.section.association.id=?1 and i.itemCondition != 'BAD' and i.itemCondition != 'PRIZE' and i.itemCondition !='LOAN'")
	Collection<Item> findAllByAssociation(int associationId);

	@Query("select i from Item i where i.section.id=?1")
	Collection<Item> findAllBySection(int sectionId);

}
