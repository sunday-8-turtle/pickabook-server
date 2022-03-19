package beside.sunday8turtle.pickabookserver.modules.bookmark.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.*;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
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
    public CommonResponse<BookmarkPostResponseDTO> postBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody BookmarkPostRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkPostResponseDTO.fromBookmark(bookmarkService.createNewBookmark(userId, dto)));
    }

    @GetMapping
    public CommonResponse<List<BookmarkGetResponseDTO>> getBookmarks(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Integer page, @RequestParam Integer size) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.getBookmarksByUserId(userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateAt")))));
    }

    @GetMapping("/tag/{tagId}")
    public CommonResponse<List<BookmarkGetResponseDTO>> getBookmarksByTagId(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long tagId, @RequestParam Integer page, @RequestParam Integer size) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.getBookmarksByUserIdAndTagId(userId, tagId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateAt")))));
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
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.searchBookmarksByUserIdAndTitleAndDescriptionAndTagName(userId, search, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateAt")))));
    }

    @GetMapping("/tags")
    public CommonResponse<List<TagGetResponseDTO>> getTags(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Integer page, @RequestParam Integer size) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(TagGetResponseDTO.fromTags(bookmarkService.getTagsByUserId(userId, PageRequest.of(page, size))));
    }

    @PostMapping("/duplication")
    public CommonResponse<BookmarkDuplicationResponseDTO> getDuplicationUrl(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody BookmarkDuplicationRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        return CommonResponse.success(BookmarkDuplicationResponseDTO.from(bookmarkService.isDuplicationUrl(userId, dto.getUrl())));
    }

}
