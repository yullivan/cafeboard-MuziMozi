package cafeboard.Board;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardRestController {
    private final BoardService boardService;

    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponseDTO createBoard(@RequestBody BoardRequestDTO request) {
        return boardService.createBoard(new Board(request.boardName()));
    }

    @GetMapping("/boards")
    public List<BoardResponseDTO> findAllBoard() {
        return boardService.findAllBoard();
    }

    @GetMapping("/boards/{boardId}")
    public BoardResponseDTO findBoard(@PathVariable Long boardId) {
        return boardService.findBoardById(boardId);
    }

    @PutMapping("/boards/{boardId}")
    public BoardResponseDTO updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDTO request) {
        boardService.updateBoard(boardId, request);
        return boardService.findBoardById(boardId);
    }

    @DeleteMapping("/boards/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
