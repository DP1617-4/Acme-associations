
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.User;

@Service
@Transactional
public class ItemService {

	public ItemService() {
		super();
	}


	//Repository
	@Autowired
	private ItemRepository	itemRepository;

	//Auxiliar Services

	@Autowired
	private UserService		userService;

	@Autowired
	private RolesService	roleService;


	public Item create() {
		Item result;

		result = new Item();

		return result;
	}

	public Item findOne(final int itemId) {
		Item result;

		result = this.itemRepository.findOne(itemId);

		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> result;

		result = this.itemRepository.findAll();

		return result;
	}

	public void delete(final Item item) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		this.roleService.checkCollaborator(principal, item.getSection().getAssociation());

		this.itemRepository.delete(item);
	}

	public Item save(final Item item) {
		final User principal = this.userService.findByPrincipal();
		this.roleService.checkCollaborator(principal, item.getSection().getAssociation());

		final Item result = this.itemRepository.save(item);

		return result;
	}

	public Item reconstruct() {
		final Item result = new Item();

		return result;
	}
}
