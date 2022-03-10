package beside.sunday8turtle.pickabookserver.modules.bookmark.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final BookmarkTagService bookmarkTagService;

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
    Page<Tag> getTagsByUserId(long userId, Pageable pageable) {
        return tagRepository.findAllTagsByUserId(userId, pageable);
    }
}