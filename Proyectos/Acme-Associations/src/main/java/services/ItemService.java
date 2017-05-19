
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Association;
import domain.Item;
import domain.Section;
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
		final String itemId = this.itemKeyGenerator();
		result.setIdentifier(itemId);

		return result;
	}

	public Item create(final Section section) {
		Item result;

		result = new Item();
		final String itemId = this.itemKeyGenerator();
		result.setIdentifier(itemId);
		result.setSection(section);

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

	public Collection<Item> findAllByAssociation(final Association association) {

		Collection<Item> result;
		result = this.itemRepository.findAllByAssociation(association.getId());
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

		this.roleService.checkCollaboratorPrincipal(item.getSection().getAssociation());

		final Item result = this.itemRepository.save(item);

		return result;
	}

	public Item reconstruct() {
		final Item result = new Item();

		return result;
	}

	public String itemKeyGenerator() {

		String result;
		final String datePattern = "yyyyMMdd";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		final String moment = simpleDateFormat.format(new Date());
		String code = "";
		code += "-" + this.randomLetter() + this.randomLetter() + this.randomLetter();
		result = moment + code;
		return result;

	}

	public String randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(62));
		return Character.toString(result);
	}

	public void changeCondition(final Item item, final String condition) {

		item.setItemCondition(condition);
		this.save(item);
	}
}
