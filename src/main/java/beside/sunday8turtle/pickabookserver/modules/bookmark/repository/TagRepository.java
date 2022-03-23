package beside.sunday8turtle.pickabookserver.modules.bookmark.repository;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findFirstByTagName(String tagName);

    void deleteById(Long tagId);

    @Query("select t from Tag t ,BookmarkTag bt, Bookmark b where b.user.id = :userId and b.id = bt.bookmarkId and t.id = bt.tagId")
    Page<Tag> findAllTagsByUserId(Long userId, Pageable pageable);
}
