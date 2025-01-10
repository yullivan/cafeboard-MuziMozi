package cafeboard.Board;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
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

    @GetMapping("/board/{boardId}")
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
