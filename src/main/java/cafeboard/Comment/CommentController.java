package cafeboard.Comment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<CommentResponseDTO> findAllComments(@RequestParam Long postId) {

        return commentService.findAllComment(postId);
    }

    @PostMapping("/comments")
    public void createComment(@RequestBody CommentRequestDTO request) {

        commentService.createComment(request.content(), request.author(), request.postId());
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
