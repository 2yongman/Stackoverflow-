package BackEnd.preProject.question.repository;

import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query(value = "SELECT q from Question q where q.questionId < ?1 order by q.questionId DESC ")
    Page<Question> findByQuestionIdLessThanOrderByQuestionIdDesc(Long lastQuestionId, PageRequest pageRequest);

    //search
    List<Question> findByTitleContainingOrderByCreatedAtDesc(String keyward);
}
