package beside.sunday8turtle.pickabookserver.modules.tag.service;

import beside.sunday8turtle.pickabookserver.modules.tag.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public List<Tag> reloadAllTagsIfAlreadyPresent(List<String> tags) {
        return tags.stream()
                .map(tag -> findByTagName(tag).orElseThrow(NoSuchElementException::new))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findByTagName(String tagName) {
        return tagRepository.findFirstByTagName(tagName);
    }
}
