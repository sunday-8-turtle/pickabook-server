package beside.sunday8turtle.pickabookserver.modules.notification.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPostResponseDTO {

    private String notiType;
    private LocalDate notidate;
    private String message;
    private String url;
    private boolean isCheck;
    private long bookmarkId;

}
