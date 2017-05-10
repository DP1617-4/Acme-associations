
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Chorbi;
import domain.Folder;

@Service
@Transactional
public class FolderService {

	//Constructor
	public FolderService() {
		super();
	}


	//Managed Repository

	@Autowired
	private FolderRepository	folderRepository;

	//Auxiliary Services

	@Autowired
	private ChorbiService		actorService;


	//CRUD

	public Folder create(final Chorbi actor) {
		final Folder result = new Folder();
		result.setChorbi(actor);
		return result;
	}

	public Folder save(final Folder folder) {
		Folder result;
		result = this.folderRepository.save(folder);
		return result;
	}

	public Folder findOne(final int folderId) {

		Folder folder;
		folder = this.folderRepository.findOne(folderId);
		return folder;
	}

	public Collection<Folder> findAllByPrincipal() {
		Chorbi actor;
		Collection<Folder> result;
		actor = this.actorService.findByPrincipal();
		result = this.folderRepository.findAllByChorbi(actor.getId());
		return result;
	}

	//Business Methods

	public Collection<Folder> initFolders(final Chorbi actor) {
		Collection<Folder> result = new ArrayList<Folder>();
		final Collection<Folder> aux = new ArrayList<Folder>();
		Folder inbox;
		Folder outbox;
		inbox = this.create(actor);
		inbox.setName("Received");
		outbox = this.create(actor);
		outbox.setName("Sent");
		aux.add(outbox);
		aux.add(inbox);
		result = this.folderRepository.save(aux);

		return result;

	}

	public void checkPrincipal(final Folder folder) {
		final Chorbi actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.equals(folder.getChorbi()), "Dear User, you can't edit a folder that doesn't belong to you");
	}

	public void checkPrincipal(final int folderId) {
		Folder folder;
		folder = this.folderRepository.findOne(folderId);
		this.checkPrincipal(folder);
	}

	public Folder findSystemFolder(final Chorbi actor, final String name) {
		Folder result;
		result = this.folderRepository.findSystemFolder(actor.getId(), name);
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.folderRepository.flush();
	}

}
