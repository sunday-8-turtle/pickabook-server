package beside.sunday8turtle.pickabookserver.modules.bookmark.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public CommonResponse<BookmarkPostResponseDTO> postBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long userId, @RequestBody BookmarkPostRequestDTO dto) {
        //TODO: 본인인증 메소드 구현 필요
        return CommonResponse.success(BookmarkPostResponseDTO.fromBookmark(bookmarkService.createNewBookmark(
                userId, dto.getTitle(), dto.getUrl(), dto.getDescription(), dto.getTag(), dto.getNotidate())));
    }

    @GetMapping("/{userId}")
    public CommonResponse<List<BookmarkGetResponseDTO>> getBookmarks(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long userId, @RequestParam Integer page, @RequestParam Integer size) {
        //TODO: 본인인증 메소드 구현 필요
        Page<Bookmark> bookmarks = bookmarkService.getBookmarkByUserId(userId, PageRequest.of(page, size));
        return CommonResponse.success(BookmarkGetResponseDTO.fromBookmarks(bookmarks));
    }


}
