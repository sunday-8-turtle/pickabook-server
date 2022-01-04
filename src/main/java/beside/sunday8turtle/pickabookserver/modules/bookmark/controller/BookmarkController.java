package beside.sunday8turtle.pickabookserver.modules.bookmark.controller;

import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkPostResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{userId}")
    public BookmarkPostResponseDTO postBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long userId, @RequestBody BookmarkPostRequestDTO dto) {
        //TODO: 본인인증 메소드 구현 필요
        return BookmarkPostResponseDTO.fromBookmark(bookmarkService.createNewBookmark(
                userId, dto.getTitle(), dto.getUrl(), dto.getDescription(), dto.getTag(), dto.getNotidate()));
    }

}
