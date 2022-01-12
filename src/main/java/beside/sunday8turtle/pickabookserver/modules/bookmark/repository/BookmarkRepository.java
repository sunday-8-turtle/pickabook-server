package beside.sunday8turtle.pickabookserver.modules.bookmark.repository;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark> findAllByUserId(long userId, Pageable pageable);

    Optional<Bookmark> findById(long bookmarkId);

    void deleteById(long bookmarkId);
}
