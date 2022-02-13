package beside.sunday8turtle.pickabookserver.modules.bookmark.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPutRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.TagGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public CommonResponse postBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody BookmarkPostRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        bookmarkService.createNewBookmark(userId, dto);
        return CommonResponse.success();
    }

    @GetMapping
    public CommonResponse<List<BookmarkGetResponseDTO>> getBookmarks(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Integer page, @RequestParam Integer size) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.getBookmarksByUserId(userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")))));
    }

    @GetMapping("/{bookmarkId}")
    public CommonResponse<BookmarkGetResponseDTO> getBookmark(@PathVariable Long bookmarkId) {
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmark(bookmarkService.getBookmarkByBookmarkId(bookmarkId)));
    }

    @DeleteMapping("/delete/{bookmarkId}")
    public CommonResponse deleteBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmarkByBookmarkId(bookmarkId);
        return CommonResponse.success();
    }

    @PutMapping("/modify/{bookmarkId}")
    public CommonResponse putBookmark(@PathVariable Long bookmarkId, @RequestBody BookmarkPutRequestDTO dto) {
        bookmarkService.updateBookmark(bookmarkId, dto);
        return CommonResponse.success();
    }

    @GetMapping("/search")
    public CommonResponse<List<BookmarkGetResponseDTO>> searchBookmarks(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String search) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.searchBookmarksByUserIdAndTitleAndDescriptionAndTagName(userId, search, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")))));
    }

    @GetMapping("/tag")
    public CommonResponse<List<TagGetResponseDTO>> getTagsByUserId(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Integer page, @RequestParam Integer size) {
        long userId = principalDetails.getUser().getId();
        List<Tag> tags = bookmarkService.getTagsByUserId(userId);
        Page<Tag> pages = new PageImpl<>(tags, PageRequest.of(page, size), tags.size());
        return CommonResponse.success(TagGetResponseDTO.fromTags(pages));
    }

}
