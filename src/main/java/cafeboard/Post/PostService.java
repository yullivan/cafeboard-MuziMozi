package cafeboard.Post;

import cafeboard.Board.BoardRepository;
import cafeboard.Comment.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private PostRepository postRepository;
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public void createPost(String title, String content, Long boardId) {

        postRepository.save(new Post(title, content, boardRepository.findById(boardId).orElseThrow()));
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

    public PostResponseDTO findPostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow();
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                commentRepository.countByPostId(post.getId()),
                post.getViewCount());
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDTO request) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다!"));
        post.setTitle(request.title());
        post.setContent(request.content());
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
