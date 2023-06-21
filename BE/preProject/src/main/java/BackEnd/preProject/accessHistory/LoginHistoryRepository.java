package BackEnd.preProject.accessHistory;

import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.question.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long>{

    }

