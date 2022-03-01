package beside.sunday8turtle.pickabookserver.modules.bookmark.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPutRequestDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserService userService;
    private final TagService tagService;
    private final BookmarkTagService bookmarkTagService;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Bookmark createNewBookmark(long userId, BookmarkPostRequestDTO request) {
        //북마크 객체 생성
        Bookmark bookmark = userService.getUserById(userId)
                .map(user -> user.addBookmark(request.getTitle(), request.getUrl(), request.getDescription(), request.getNotidate(), user))
                .map(bookmarkRepository::save)
                .orElseThrow(NoSuchElementException::new);
        //북마크태그, 태그 생성
        List<BookmarkTag> bookmarkTags = request.getTags().stream()
                .map(tag -> tagService.createTag(tag))
                .map(tag -> bookmarkTagService.createBookmarkTag(bookmark.getId(), tag.getId()))
                .collect(Collectors.toList());
        //북마크에 북마크태그값 주입
        bookmark.setBookmarkTag(bookmarkTags);
        return bookmark;
    }

    @Transactional(readOnly = true)
    public Page<Bookmark> getBookmarksByUserId(long userId, Pageable pageable) {
        return userService.getUserById(userId)
                .map(user -> bookmarkRepository.findAllByUserId(user.getId(), pageable))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarksByUserId(long userId) {
        return userService.getUserById(userId)
                .map(user -> bookmarkRepository.findAllByUserId(user.getId()))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Page<Bookmark> getBookmarksByUserIdAndTagId(long userId, long tagId, Pageable pageable) {
        return userService.getUserById(userId)
                .map(user -> bookmarkRepository.findAllByUserIdAndTagId(user.getId(), tagId, pageable))
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
        //북마크태그에 연계되어있지 않은 태그들 삭제
        tagService.deleteTagIfNotPresentBookmarkTag();
    }

    @Transactional
    public Bookmark updateBookmark(long bookmarkId, BookmarkPutRequestDTO request) {

        //북마크 체크후 태그필드 빼고 업데이트
        bookmarkRepository.findById(bookmarkId).ifPresent(bookmark -> bookmark.updateBookmark(bookmark, request.toUpdateRequest()));
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(NoSuchElementException::new);

        //북마크태그 객체 생성
        List<BookmarkTag> bookmarkTags = request.getTags().stream()
                .map(tag -> tagService.createTag(tag))
                .map(tag -> bookmarkTagService.createBookmarkTag(bookmark.getId(), tag.getId()))
                .collect(Collectors.toList());

        //북마크 태그 초기화
        bookmarkTagService.deleteBookmarkTagByBookmarkId(bookmarkId);

        //북마크 안에 북마크태그값 주입
        bookmark.setBookmarkTag(bookmarkTags);

        //북마크태그에 연계되어있지 않은 태그들 삭제
        tagService.deleteTagIfNotPresentBookmarkTag();
        return bookmark;
    }

    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarkByBrowserNoti(LocalDate notidate) {
        return bookmarkRepository.findAllByBrowserNoti(notidate);
    }

    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarkByEmailNoti(LocalDate notidate) {
        return bookmarkRepository.findAllByEmailNoti(notidate);
    }

    @Transactional(readOnly = true)
    public Page<Bookmark> searchBookmarksByUserIdAndTitleAndDescriptionAndTagName(long userId, String search, Pageable pageable) {
        return userService.getUserById(userId)
                .map(user -> bookmarkRepository.searchAllByUserIdAndTitleOrDescriptionOrTagName(user.getId(), search, pageable))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Page<Tag> getTagsByUserId(long userId, Pageable pageable) {
        return tagService.getTagsByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicationUrl(long userId, String url) {
        int size = userService.getUserById(userId)
                .map(user -> bookmarkRepository.findAllByUserIdAndUrl(user.getId(), url))
                .orElseThrow(NoSuchElementException::new).size();
        return size > 0;
    }

}
