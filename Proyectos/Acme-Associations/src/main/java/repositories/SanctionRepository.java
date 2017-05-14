
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Sanction;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

}
