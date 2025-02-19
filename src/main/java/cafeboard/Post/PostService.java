package cafeboard.Post;

import cafeboard.Board.BoardRepository;
import cafeboard.Comment.CommentRepository;
import cafeboard.Comment.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, BoardRepository boardRepository, CommentRepository commentRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    public PostCreateResponseDTO createPost(String title, String content, Long boardId) {

        Post post = postRepository.save(new Post(title, content, boardRepository.findById(boardId).orElseThrow()));
        return new PostCreateResponseDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt() ,post.getViewCount());
    }

    public List<PostResponseDTO> findAllPost(Long boardID) {

        List<Post> posts = postRepository.findByBoardId(boardID);
        return posts.stream().map(post -> new PostResponseDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    commentRepository.countByPostId(post.getId()),
                    post.getViewCount())).toList();
    }

    public PostDetailResponseDTO findPostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow();
        return new PostDetailResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                commentRepository.countByPostId(post.getId()),
                post.getViewCount(),
                commentService.findAllComment(postId));
    }

    @Transactional
    public PostDetailResponseDTO updatePost(Long postId, PostRequestDTO request) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다!"));
        post.setTitle(request.title());
        post.setContent(request.content());
        return findPostById(postId);
    }

    public void deletePost(Long postId) {

        postRepository.delete(postRepository.findById(postId).orElseThrow());
    }

    @Transactional
    public void increaseViewCount(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다!"));
        post.setViewCount(post.getViewCount() + 1);
    }
}
