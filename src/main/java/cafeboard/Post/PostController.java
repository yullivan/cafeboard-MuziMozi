package cafeboard.Post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostResponseDTO> getPosts(@RequestParam Long boardId) {

        return postService.findAllPost(boardId);
    }

    @GetMapping("/posts/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {

        postService.increaseViewCount(postId);
        return postService.findPostById(postId);
    }

    @PostMapping("/posts")
    public void createPost(@RequestBody PostRequestDTO request) {

        postService.createPost(request.title(), request.content(), request.boardId());
    }

    @PutMapping("/posts/{postId}")
    public PostResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO request) {

        postService.updatePost(postId, request);
        return postService.findPostById(postId);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {

        postService.deletePost(postId);
    }
}
