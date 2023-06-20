package BackEnd.preProject.question.repository;

import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllByOrderByQuestionIdDesc(Pageable page);

    List<Question> findByQuestionIdLessThanOrderByQuestionIdDesc(Long questionId, Pageable page);

    Boolean existsByQuestionIdLessThan(Long questiondId);

    //search
    List<Question> findByTitleContainingOrderByCreatedAtDesc(String keyward);
}
