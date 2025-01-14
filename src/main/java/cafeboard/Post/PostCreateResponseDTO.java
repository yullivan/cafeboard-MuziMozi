package cafeboard.Post;

import java.time.LocalDateTime;

public record PostCreateResponseDTO(Long id, String title, String content, LocalDateTime createdAt, int viewCount) {
}
