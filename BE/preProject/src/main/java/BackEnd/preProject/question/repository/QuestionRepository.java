package BackEnd.preProject.question.repository;

import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllByOrderByQuestionIdDesc(Pageable page);

    List<Question> findByQuestionIdLessThanOrderByQuestionIdDesc(Long questionId, Pageable page);

    Boolean existsByQuestionIdLessThan(Long questionId);

    //search
    List<Question> findByTitleContainingOrderByCreatedAtDesc(String keyword);

    List<Question> findByMemberMemberId(Long memberId);
}
