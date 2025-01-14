package cafeboard;

import cafeboard.Board.Board;
import cafeboard.Board.BoardResponseDTO;
import cafeboard.Board.BoardService;
import cafeboard.Post.PostResponseDTO;
import cafeboard.Post.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
class BoardController {

    private final BoardService boardService;
    private final PostService postService;  // 게시글 관련 서비스

    public BoardController(BoardService boardService, PostService postService) {
        this.boardService = boardService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String readAllBoards(Model model) {
        List<BoardResponseDTO> boards = boardService.findAllBoard(); // 게시판 목록 가져오기
        model.addAttribute("boards", boards); // 모델에 데이터 추가
        return "index"; // main.html 렌더링
    }

    @GetMapping("/create-board")
    public String showCreateBoardForm() {
        return "create-board";  // 게시판 생성 HTML 폼
    }

    // 게시판 생성 처리하는 POST 요청
    @PostMapping("/boards")
    public String createBoard(@RequestParam("boardName") String boardName) {
        // 게시판 이름이 비어있으면 처리할 로직 (optional)
        if (boardName == null || boardName.trim().isEmpty()) {
            return "redirect:/create-board?error";  // 에러 처리 후 다시 폼을 보이도록 리다이렉트
        }

        // 게시판 생성 서비스 호출
        boardService.createBoard(new Board(boardName));

        // 게시판 목록 페이지로 리다이렉트
        return "redirect:/";
    }

    // 게시판 상세 페이지 (게시판의 게시글 목록을 표시)
    @GetMapping("/boards/{boardId}")
    public String viewBoard(@PathVariable("boardId") Long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoardById(boardId);  // 게시판 정보 조회
        List<PostResponseDTO> posts = postService.findAllPost(boardId);  // 게시판에 속한 게시글 목록 조회

        model.addAttribute("board", board);  // 게시판 정보
        model.addAttribute("posts", posts);  // 게시글 목록
        return "board-detail";  // 게시판 상세 페이지 뷰
    }

    // 게시글 작성 페이지로 이동 (게시글 생성 버튼)
    @GetMapping("/boards/{boardId}/create-post")
    public String createPostForm(@PathVariable("boardId") Long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoardById(boardId);
        model.addAttribute("board", board);
        return "create-post";  // 게시글 작성 폼
    }
}
