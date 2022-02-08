package beside.sunday8turtle.pickabookserver.modules.tag.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.bookmarktag.service.BookmarkTagService;
import beside.sunday8turtle.pickabookserver.modules.tag.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.tag.repository.TagRepository;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final BookmarkTagService bookmarkTagService;
    private final UserService userService;
    private final BookmarkService bookmarkService;

    @Transactional
    public Tag createTag(String tagName) {
        return tagRepository.findFirstByTagName(tagName).orElseGet(() -> tagRepository.save(Tag.of(tagName)));
    }


    @Transactional
    public void deleteTagIfNotPresentBookmarkTag() {
        List<Tag> tags = tagRepository.findAll();
        tags.forEach(tag -> bookmarkTagService.findBookmarkTagByTagId(tag.getId()).orElseGet(() -> {
            tagRepository.deleteById(tag.getId());
            return null;
        }));
    }

    @Transactional(readOnly = true)
    public List<Tag> getTagsByUserId(long userId) {
        //user->List<bookmark>->bookmarkTag->tag
        List<Tag> tagList = new ArrayList<>();
        List<BookmarkTag> bookmarkTagList = new ArrayList<>();
        List<Bookmark> bookmarks = userService.getUserById(userId)
                .map(user -> bookmarkService.getBookmarksByUserId(user.getId())).orElseThrow(NoSuchElementException::new);
        bookmarks.forEach(bookmark -> bookmarkTagList.add(bookmarkTagService.findBookmarkTagByBookmarkId(bookmark.getId()).orElseThrow(NoSuchElementException::new)));
        bookmarkTagList.forEach(bookmarkTag -> tagList.add(tagRepository.findFirstById(bookmarkTag.getTagId()).orElseThrow(NoSuchElementException::new)));
        return tagList;
    }

}
