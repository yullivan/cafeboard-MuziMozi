package cafeboard.Post;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String title, String content, LocalDateTime createdAt, int commentCount, int viewCount) {
}
