package beside.sunday8turtle.pickabookserver.modules.tag.service;

import beside.sunday8turtle.pickabookserver.modules.bookmarktag.repository.BookmarkTagRepository;
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
    private final BookmarkTagRepository bookmarkTagRepository;

    @Transactional
    public Tag createTag(String tagName) {
        return tagRepository.findFirstByTagName(tagName).orElseGet(() -> tagRepository.save(Tag.of(tagName)));
    }


    @Transactional
    public void deleteTagIfNotPresentBookmarkTag() {
        List<Tag> tags = tagRepository.findAll();
        tags.forEach(tag -> bookmarkTagRepository.findFirstByTagId(tag.getId()).orElseGet(() -> {
            tagRepository.deleteById(tag.getId());
            return null;
        }));

    }
}
