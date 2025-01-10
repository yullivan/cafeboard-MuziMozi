package cafeboard.Post;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String title, LocalDateTime createdAt, int commentCount, int viewCount) {
}
