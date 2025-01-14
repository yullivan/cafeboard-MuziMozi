package cafeboard.Post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostResponseDTO> getPosts(@RequestParam Long boardId) {

        return postService.findAllPost(boardId);
    }

    @GetMapping("/posts/{postId}")
    public PostDetailResponseDTO getPost(@PathVariable Long postId) {

        postService.increaseViewCount(postId);
        return postService.findPostById(postId);
    }

    @PostMapping("/posts")
    public PostCreateResponseDTO createPost(@RequestBody PostRequestDTO request) {

        return postService.createPost(request.title(), request.content(), request.boardId());
    }

    @PutMapping("/posts/{postId}")
    public PostDetailResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO request) {

        return postService.updatePost(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {

        postService.deletePost(postId);
    }
}
