package beside.sunday8turtle.pickabookserver.modules.user.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkUpdateRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String nickname;
    private String roles; // USER, ADMIN

    private User(String email, String password, String nickname, String roles) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
    }

    protected User() {
    }

    public static User of(String email, String password, String nickname, String roles) {
        return new User(email, password, nickname, roles);
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, password);
    }

    public Bookmark addBookmark(String title, String url, String description, String tag, Date notidate, User user) {
        return Bookmark.of(title, url, description, tag, notidate, user);
    }

    public Bookmark updateBookmark(Bookmark bookmark, BookmarkUpdateRequest request) {
        bookmark.updateBookmark(request);
        return bookmark;
    }

}
