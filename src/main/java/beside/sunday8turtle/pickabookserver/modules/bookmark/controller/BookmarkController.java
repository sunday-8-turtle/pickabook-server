package beside.sunday8turtle.pickabookserver.modules.bookmark.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPutRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPutResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{userId}")
    public CommonResponse postBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long userId, @RequestBody BookmarkPostRequestDTO dto) {
        //TODO: 본인인증 메소드 구현 필요
        bookmarkService.createNewBookmark(userId, dto);
        return CommonResponse.success();
    }

    @GetMapping("/{userId}")
    public CommonResponse<List<BookmarkGetResponseDTO>> getBookmarks(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long userId, @RequestParam Integer page, @RequestParam Integer size) {
        //TODO: 본인인증 메소드 구현 필요
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarkService.getBookmarksByUserId(userId, PageRequest.of(page, size))));
    }

    @GetMapping("/detail/{bookmarkId}")
    public CommonResponse<BookmarkGetResponseDTO> getBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long bookmarkId) {
        //TODO: 본인인증 메소드 구현 필요
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmark(bookmarkService.getBookmarkByBookmarkId(bookmarkId)));
    }

    @DeleteMapping("/detail/{bookmarkId}")
    public CommonResponse deleteBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long bookmarkId) {
        //TODO: 본인인증 메소드 구현 필요
        bookmarkService.deleteBookmarkByBookmarkId(bookmarkId);
        return CommonResponse.success();
    }

    @PutMapping("/detail/{bookmarkId}")
    public CommonResponse<BookmarkPutResponseDTO> putBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long bookmarkId, @RequestBody BookmarkPutRequestDTO dto) {
        //TODO: 본인인증 메소드 구현 필요
        return CommonResponse.success(BookmarkPutResponseDTO.fromBookmark(bookmarkService.updateBookmark(bookmarkId, dto.toUpdateRequest())));
    }

}
