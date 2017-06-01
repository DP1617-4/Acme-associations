
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select l.item from Loan l right JOIN l.item i where l.finalDate!=null and i.section.association.id=?1 and i.itemCondition != 'BAD' and i.itemCondition != 'PRIZE' and i.itemCondition !='LOAN'")
	Collection<Item> findAllByAssociationLoaned(int associationId);

	@Query("select i from Loan l right join l.item i where i.section.association.id=?1 and i.itemCondition != 'BAD' and i.itemCondition != 'PRIZE' and i.itemCondition !='LOAN' and l.finalDate != null")
	Collection<Item> findAllByAssociation(int associationId);

	@Query("select i from Item i where i.section.id=?1")
	Collection<Item> findAllBySection(int sectionId);

	@Query("select i from Item i where i.itemCondition!='LOAN' and (i.name like %?1% or i.identifier=?1 or lower(i.itemCondition) like lower(?1) or i.description like %?1% or i.section.name like %?1% or i.section.association.name like %?1%)")
	Collection<Item> filterItems(String filter);

	@Query("select l.item from Loan l where l.item.section.association.id=?1 group by l.item order by count(l) DESC")
	Collection<Item> findMostLoanedItemByAssociation(int associationId);

	@Query("select count(l) from Loan l where l.item.id=?1")
	Integer countLoansItem(int itemId);
}
