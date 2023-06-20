package BackEnd.preProject.answer.repository;

import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByQuestionOrderByAnswerIdDesc(Question question, Pageable page);

    List<Answer> findByQuestionAndAnswerIdLessThanOrderByAnswerIdDesc(Question question, Long cursorId, Pageable page);

    Boolean existsByAnswerIdLessThan(Long cursorId);
}
