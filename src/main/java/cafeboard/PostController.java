package cafeboard;

import cafeboard.Board.Board;
import cafeboard.Board.BoardResponseDTO;
import cafeboard.Board.BoardService;
import cafeboard.Post.Post;
import cafeboard.Post.PostRequestDTO;
import cafeboard.Post.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    public PostController(PostService postService, BoardService boardService) {
        this.postService = postService;
        this.boardService = boardService;
    }

    @PostMapping("/boards/{boardId}/posts")
    public String createPost(@PathVariable("boardId") Long boardId, @ModelAttribute PostRequestDTO request) {

        postService.createPost(request.title(), request.content(), boardId);

        return "redirect:/boards/" + boardId;
    }

}
