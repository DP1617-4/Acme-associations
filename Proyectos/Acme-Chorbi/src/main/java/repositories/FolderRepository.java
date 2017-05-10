
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Folder f where f.chorbi.id = ?1")
	Collection<Folder> findAllByChorbi(int id);

	@Query("select f from Folder f where f.chorbi.id = ?1 and f.name = ?2")
	Folder findSystemFolder(int id, String name);

}
