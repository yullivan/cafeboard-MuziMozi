package cafeboard.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(Long id, String content, LocalDateTime createdAt, String author) {
}
