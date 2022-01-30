package beside.sunday8turtle.pickabookserver.modules.user.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    // noti setting
    private boolean isBrowserNoti;
    private boolean isEmailNoti;

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

    public Bookmark addBookmark(String title, String url, String description, LocalDate notidate, User user) {
        return Bookmark.of(title, url, description, notidate, user);
    }

    public void enableBrowserNoti() {
        this.isBrowserNoti = true;
    }

    public void disableBrowserNoti() {
        this.isBrowserNoti = false;
    }

    public void enableEmailNoti() {
        this.isEmailNoti = true;
    }

    public void disableEmailNoti() {
        this.isEmailNoti = false;
    }


}
