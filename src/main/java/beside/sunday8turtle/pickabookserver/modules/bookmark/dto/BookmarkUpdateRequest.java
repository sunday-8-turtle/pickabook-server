package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class BookmarkUpdateRequest {
    private final String titleToUpdate;
    private final String urlToUpdate;
    private final String descriptionToUpdate;
    private final String tagToUpdate;
    private final Date notidateToUpdate;

    public static BookmarkUpdateRequestBuilder builder() {
        return new BookmarkUpdateRequestBuilder();
    }

    public Optional<String> getTitleToUpdate() {
        return ofNullable(titleToUpdate);
    }
    public Optional<String> getUrlToUpdate() {
        return ofNullable(urlToUpdate);
    }
    public Optional<String> getDescriptionToUpdate() {
        return ofNullable(descriptionToUpdate);
    }
    public Optional<String> getTagToUpdate() {
        return ofNullable(tagToUpdate);
    }
    public Optional<Date> getNotidateToUpdate() {
        return ofNullable(notidateToUpdate);
    }

    private BookmarkUpdateRequest(BookmarkUpdateRequestBuilder builder) {
        this.titleToUpdate = builder.titleToUpdate;
        this.urlToUpdate = builder.urlToUpdate;
        this.descriptionToUpdate = builder.descriptionToUpdate;
        this.tagToUpdate = builder.tagToUpdate;
        this.notidateToUpdate = builder.notidateToUpdate;
    }

    public static class BookmarkUpdateRequestBuilder {
        private String titleToUpdate;
        private String urlToUpdate;
        private String descriptionToUpdate;
        private String tagToUpdate;
        private Date notidateToUpdate;

        public BookmarkUpdateRequestBuilder titleToUpdate(String titleToUpdate) {
            this.titleToUpdate = titleToUpdate;
            return this;
        }

        public BookmarkUpdateRequestBuilder urlToUpdate(String urlToUpdate) {
            this.urlToUpdate = urlToUpdate;
            return this;
        }

        public BookmarkUpdateRequestBuilder descriptionToUpdate(String descriptionToUpdate) {
            this.descriptionToUpdate = descriptionToUpdate;
            return this;
        }

        public BookmarkUpdateRequestBuilder tagToUpdate(String tagToUpdate) {
            this.tagToUpdate = tagToUpdate;
            return this;
        }

        public BookmarkUpdateRequestBuilder notidateToUpdate(Date notidateToUpdate) {
            this.notidateToUpdate = notidateToUpdate;
            return this;
        }

        public BookmarkUpdateRequest build() {
            return new BookmarkUpdateRequest(this);
        }
    }
}
