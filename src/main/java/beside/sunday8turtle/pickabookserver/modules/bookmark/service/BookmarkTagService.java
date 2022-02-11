package beside.sunday8turtle.pickabookserver.modules.bookmark.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.repository.BookmarkTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkTagService {

    private final BookmarkTagRepository bookmarkTagRepository;

    @Transactional
    public BookmarkTag createBookmarkTag(long bookmarkId, long tagId) {
        return bookmarkTagRepository.findFirstByBookmarkIdAndTagId(bookmarkId, tagId).orElseGet(() -> bookmarkTagRepository.save(BookmarkTag.of(bookmarkId, tagId)));
    }

    @Transactional
    public void deleteBookmarkTagByBookmarkId(long bookmarkId) {
        bookmarkTagRepository.deleteByBookmarkId(bookmarkId);
    }

    @Transactional(readOnly = true)
    public Optional<BookmarkTag> findBookmarkTagByTagId(long tagId) {
        return bookmarkTagRepository.findFirstByTagId(tagId);
    }

    @Transactional(readOnly = true)
    public List<BookmarkTag> findBookmarkTagsByBookmarkId(long bookmarkId) {
        return bookmarkTagRepository.findAllByBookmarkId(bookmarkId);
    }

}
