package beside.sunday8turtle.pickabookserver.modules.bookmark.repository;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark> findAllByUserId(long userId, Pageable pageable);

    @Query("select b from Bookmark b ,BookmarkTag t where b.user.id = :userId and b.id = t.bookmarkId and t.tagId = :tagId")
    Page<Bookmark> findAllByUserIdAndTagId(long userId, long tagId, Pageable pageable);

    List<Bookmark> findAllByUserId(long userId);

    Optional<Bookmark> findById(long bookmarkId);

    void deleteById(long bookmarkId);

    @Query("select DISTINCT b from Bookmark b ,BookmarkTag t where b.user.id = :userId and b.id = t.bookmarkId and(b.title LIKE CONCAT('%',:search,'%') or b.description LIKE CONCAT('%',:search,'%') or t.tag.tagName LIKE CONCAT('%',:search,'%'))")
    Page<Bookmark> searchAllByUserIdAndTitleOrDescriptionOrTagName(long userId, String search, Pageable pageable);

    @Query("select b from Bookmark b where b.notidate = :searchDate and b.user.isBrowserNoti = true")
    List<Bookmark> findAllByBrowserNoti(@Param("searchDate") LocalDate searchDate);

    @Query("select b from Bookmark b where b.notidate = :searchDate and b.user.isEmailNoti = true")
    List<Bookmark> findAllByEmailNoti(@Param("searchDate") LocalDate searchDate);

    List findAllByUserIdAndUrl(long userId, String url);
}
