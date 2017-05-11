
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
