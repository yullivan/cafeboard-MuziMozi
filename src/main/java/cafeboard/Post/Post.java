package cafeboard.Post;

import cafeboard.Board.Board;
import cafeboard.UserInfo.UserInfo;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int viewCount;

    @ManyToOne
    private Board board;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments;
    @OneToOne
    private UserInfo userInfo;

    public Post() {
    }

    public Post(String title, String content, Board board) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.viewCount = 0;
    }

    public UserInfo getUser() {
        return userInfo;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public Board getBoard() {
        return board;
    }

//    public List<Comment> getComments() {
//        return comments;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
