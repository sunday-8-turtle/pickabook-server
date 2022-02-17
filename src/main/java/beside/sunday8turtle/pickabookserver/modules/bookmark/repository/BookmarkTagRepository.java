package beside.sunday8turtle.pickabookserver.modules.bookmark.repository;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.BookmarkTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkTagRepository extends JpaRepository<BookmarkTag, Long> {
    Optional<BookmarkTag> findFirstByBookmarkIdAndTagId(Long bookmarkId, Long tagId);

    Optional<BookmarkTag> findFirstByTagId(Long tagId);

    void deleteByBookmarkId(Long BookmarkId);

    List<BookmarkTag> findAllByBookmarkId(Long bookmarkId);
}
