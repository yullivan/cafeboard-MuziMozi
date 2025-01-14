package cafeboard.Post;

import cafeboard.Comment.CommentResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponseDTO(Long id, String title, String content, LocalDateTime createdAt, int commentCount, int viewCount, List<CommentResponseDTO> comments) {
}
