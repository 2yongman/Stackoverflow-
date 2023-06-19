package BackEnd.preProject.question.repository;

import BackEnd.preProject.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
