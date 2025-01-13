package cafeboard;

import cafeboard.Board.BoardResponseDTO;
import cafeboard.Board.BoardService;
import cafeboard.Post.Post;
import cafeboard.Post.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    public PostController(PostService postService, BoardService boardService) {
        this.postService = postService;
        this.boardService = boardService;
    }

    @PostMapping("/boards/{boardId}/posts")
    public String createPost(@PathVariable("boardId") Long boardId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        postService.createPost(title, content, boardId);

        return "redirect:/boards/" + boardId;  // 게시글 작성 후 해당 게시판으로 리다이렉트
    }

}
