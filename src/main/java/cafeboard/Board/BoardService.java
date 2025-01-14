package cafeboard.Board;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponseDTO createBoard(Board board) {

        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDTO(savedBoard.getId(), savedBoard.getBoardName());
    }

    public List<BoardResponseDTO> findAllBoard() {

        return boardRepository.findAll()
                .stream()
                .map(board -> new BoardResponseDTO(
                        board.getId(),
                        board.getBoardName()))
                .toList();
    }
    public BoardResponseDTO findBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow();
        return new BoardResponseDTO(board.getId(), board.getBoardName());
    }

    @Transactional
    public void updateBoard(Long boardId, BoardRequestDTO request) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시판이 존재하지 않습니다!"));
        board.setBoardName(request.boardName());
    }

    public void deleteBoard(Long boardId) {

        boardRepository.delete(boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시판이 존재하지 않습니다!")));
    }
}
