package nl.han.devops.repositories;

import nl.han.devops.dto.ProjectDTO;
import nl.han.devops.dto.UserDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectDTO, Integer> {
//    Optional<ProjectDTO> findByStudent_Id(int student_id);
//    Optional<ProjectDTO> findFirstByStudent_idOrderByUpdated_atDesc(int studentId);
    Optional<ProjectDTO> findFirstByStudentidOrderByUpdatedatDesc(int studentId);
}