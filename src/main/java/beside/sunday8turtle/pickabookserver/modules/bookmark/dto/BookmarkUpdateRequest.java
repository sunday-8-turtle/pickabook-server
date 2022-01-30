package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class BookmarkUpdateRequest {
    private final String titleToUpdate;
    private final String urlToUpdate;
    private final String descriptionToUpdate;
    private final List<BookmarkTag> bookmarkTagsToUpdate;
    private final LocalDate notidateToUpdate;

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

    public Optional<List<BookmarkTag>> getBookmarkTagsToUpdate() {
        return ofNullable(bookmarkTagsToUpdate);
    }

    public Optional<LocalDate> getNotidateToUpdate() {
        return ofNullable(notidateToUpdate);
    }

    private BookmarkUpdateRequest(BookmarkUpdateRequestBuilder builder) {
        this.titleToUpdate = builder.titleToUpdate;
        this.urlToUpdate = builder.urlToUpdate;
        this.descriptionToUpdate = builder.descriptionToUpdate;
        this.bookmarkTagsToUpdate = builder.bookmarkTagsToUpdate;
        this.notidateToUpdate = builder.notidateToUpdate;
    }

    public static class BookmarkUpdateRequestBuilder {
        private String titleToUpdate;
        private String urlToUpdate;
        private String descriptionToUpdate;
        private List<BookmarkTag> bookmarkTagsToUpdate;
        private LocalDate notidateToUpdate;

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

        public BookmarkUpdateRequestBuilder bookmarkTagListToUpdate(List<BookmarkTag> bookmarkTagsToUpdate) {
            this.bookmarkTagsToUpdate = bookmarkTagsToUpdate;
            return this;
        }

        public BookmarkUpdateRequestBuilder notidateToUpdate(LocalDate notidateToUpdate) {
            this.notidateToUpdate = notidateToUpdate;
            return this;
        }

        public BookmarkUpdateRequest build() {
            return new BookmarkUpdateRequest(this);
        }
    }
}
