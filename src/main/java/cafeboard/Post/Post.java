package cafeboard.Post;

import cafeboard.BaseEntity;
import cafeboard.Board.Board;
import cafeboard.UserInfo.UserInfo;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    @ManyToOne
    private Board board;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments;
    @ManyToOne
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

    public void setBoard(Board board) {
        this.board = board;
    }
}
