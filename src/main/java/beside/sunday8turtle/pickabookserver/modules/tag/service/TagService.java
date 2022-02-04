package beside.sunday8turtle.pickabookserver.modules.tag.service;

import beside.sunday8turtle.pickabookserver.modules.bookmarktag.service.BookmarkTagService;
import beside.sunday8turtle.pickabookserver.modules.tag.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
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

}
