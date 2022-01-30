package beside.sunday8turtle.pickabookserver.modules.bookmarktag.service;

import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.bookmarktag.repository.BookmarkTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkTagService {

    private final BookmarkTagRepository bookmarkTagRepository;

    @Transactional
    public BookmarkTag createBookmarkTag(long bookmarkId, long tagId) {
        return bookmarkTagRepository.findFirstByBookmarkIdAndTagId(bookmarkId, tagId).orElseGet(()->bookmarkTagRepository.save(BookmarkTag.of(bookmarkId,tagId)));
    }

    @Transactional
    public void deleteBookmarkTagByBookmarkId(long bookmarkId) {
        bookmarkTagRepository.deleteByBookmarkId(bookmarkId);
    }
}
