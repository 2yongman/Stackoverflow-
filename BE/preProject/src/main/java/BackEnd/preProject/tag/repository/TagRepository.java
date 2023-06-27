package BackEnd.preProject.tag.repository;

import BackEnd.preProject.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
