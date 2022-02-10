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

    List<Bookmark> findAllByUserId(long userId);

    Optional<Bookmark> findById(long bookmarkId);

    void deleteById(long bookmarkId);

    List<Bookmark> findAllByNotidate(LocalDate Notidate);

    @Query("select b from Bookmark b where b.notidate = :searchDate and b.user.isBrowserNoti = true")
    List<Bookmark> findAllByBrowserNoti(@Param("searchDate") LocalDate searchDate);

    @Query("select b from Bookmark b where b.notidate = :searchDate and b.user.isEmailNoti = true")
    List<Bookmark> findAllByEmailNoti(@Param("searchDate") LocalDate searchDate);
}
