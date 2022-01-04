package beside.sunday8turtle.pickabookserver.modules.bookmark.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.repository.BookmarkRepository;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Bookmark createNewBookmark(long id, String title, String url, String description, String tag, Date notidate) {
        return userService.getUserById(id)
                .map(user -> user.addBookmark(title, url, description, tag, notidate, user))
                .map(bookmarkRepository::save)
                .orElseThrow(NoSuchElementException::new);
    }

}
