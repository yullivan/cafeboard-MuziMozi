package cafeboard.Comment;

import cafeboard.Post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponseDTO> findAllComment(Long postId) {

        List<Comment> comments= commentRepository.findByPostId(postId);
        return comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getAuthor()
                )).toList();
    }

    public void createComment(String content, String author, Long postId) {

        commentRepository.save(new Comment(content, author, postRepository.findById(postId).orElseThrow()));
    }

    public void updateComment(Long commentId, CommentRequestDTO request) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다!"));
        comment.setContent(request.content());
        comment.setAuthor(request.author());
    }

    public CommentResponseDTO findCommentById(Long commentId) {

        Comment comment= commentRepository.findById(commentId).orElseThrow();
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getAuthor());
    }

    public void deleteComment(Long commentId) {

        commentRepository.delete(commentRepository.findById(commentId).orElseThrow());
    }
}
