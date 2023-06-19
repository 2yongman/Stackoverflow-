package BackEnd.preProject.answer.repository;

import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "select a from Answer a WHERE a.question = ?1 AND a.answerId < ?2 order by a.answerId DESC")
    Page<Answer> findByQuestionAndAnswerIdLessThanOrderByAnswerIdDesc(Question question, Long lastAnswerId, PageRequest pageRequest);
}
