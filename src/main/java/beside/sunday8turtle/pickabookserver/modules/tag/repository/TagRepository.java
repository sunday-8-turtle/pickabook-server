package beside.sunday8turtle.pickabookserver.modules.tag.repository;

import beside.sunday8turtle.pickabookserver.modules.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findFirstByTagName(String tagName);

    void deleteById(Long tagId);

    Optional<Tag> findFirstById(Long tagId);

}
