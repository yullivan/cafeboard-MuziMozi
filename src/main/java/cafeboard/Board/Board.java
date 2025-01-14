package cafeboard.Board;

import jakarta.persistence.*;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String boardName;

//    @OneToMany(mappedBy = "board")
//    private List<Post> posts;

    protected Board() {
    }

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public Long getId() {
        return id;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
//    public List<Post> getPosts() {
//        return posts;
//    }
}
