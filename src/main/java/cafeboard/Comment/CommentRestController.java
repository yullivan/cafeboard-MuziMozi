package cafeboard.Comment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<CommentResponseDTO> findAllComments(@RequestParam Long postId) {

        return commentService.findAllComment(postId);
    }

    @PostMapping("/comments")
    public CommentResponseDTO createComment(@RequestBody CommentRequestDTO request) {

        return commentService.createComment(request.content(), request.author(), request.postId());
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDTO updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO request) {

        commentService.updateComment(commentId, request);
        return commentService.findCommentById(commentId);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
    }
}
