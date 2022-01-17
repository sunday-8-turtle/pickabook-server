package beside.sunday8turtle.pickabookserver.modules.bookmark.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkUpdateRequest;
import beside.sunday8turtle.pickabookserver.modules.bookmark.repository.BookmarkRepository;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Bookmark createNewBookmark(long userId, BookmarkPostRequestDTO request) {
        return userService.getUserById(userId)
                .map(user -> user.addBookmark(request.getTitle(), request.getUrl(), request.getDescription(), request.getTag(), request.getNotidate(), user))
                .map(bookmarkRepository::save)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Page<Bookmark> getBookmarksByUserId(long userId, Pageable pageable) {
        return userService.getUserById(userId)
                .map(user -> bookmarkRepository.findAllByUserId(user.getId(), pageable))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Bookmark getBookmarkByBookmarkId(long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deleteBookmarkByBookmarkId(long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    @Transactional
    public Bookmark updateBookmark(long bookmarkId, BookmarkUpdateRequest request) {
        //TODO: bookmark도메인에 수정 메소드 작성
        bookmarkRepository.findById(bookmarkId).ifPresent(bookmark -> bookmark.updateBookmark(bookmark, request));
        return bookmarkRepository.findById(bookmarkId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarkByNotidate(LocalDate notidate) {
        //TODO: bookmark도메인에 수정 메소드 작성
        return bookmarkRepository.findAllByNotidate(notidate);
    }
}
