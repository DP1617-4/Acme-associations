
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private FolderService	folderService;

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverInitFolder() {
		final Object testingData[][] = {
			{		// Comprobacion correcta: username con sus folders.
				"chorbi1", null
			}, {	// Comprobacion erronea: username vacío.
				"", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateInitFolder((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Templates ----------------------------------------------------------
	protected void templateInitFolder(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.findOne(this.extract(username));
			final Collection<Folder> init = this.folderService.initFolders(chorbi);
			this.folderService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
