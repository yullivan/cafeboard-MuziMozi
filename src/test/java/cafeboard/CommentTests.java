package cafeboard;

import cafeboard.Board.BoardRequestDTO;
import cafeboard.Comment.CommentRequestDTO;
import cafeboard.Comment.CommentResponseDTO;
import cafeboard.Post.PostCreateResponseDTO;
import cafeboard.Post.PostRequestDTO;
import cafeboard.Post.PostResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTests extends TestSetting{

    @DisplayName("댓글을 생성한다.")
    @Test
    void createComment() {
        // 게시판 생성
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new BoardRequestDTO("공지사항"))
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.OK.value());

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // 댓글 생성
        CommentResponseDTO comment = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequestDTO("댓글", "작성자1", 1L))
                .when()
                .post("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CommentResponseDTO.class);

        assertThat(comment.id()).isGreaterThan(0);
    }

    @DisplayName("댓글 목록을 조회한다.")
    @Test
    void findComments() {
        // given
        // 게시판 생성
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new BoardRequestDTO("공지사항"))
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.OK.value());

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // 댓글 생성
        CommentResponseDTO comment = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequestDTO("댓글", "작성자1", 1L))
                .when()
                .post("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CommentResponseDTO.class);

        // when
        // 댓글 목록 조회
        List<CommentResponseDTO> comments = RestAssured
                .given().log().all()
                .queryParam("postId", 1L)
                .when()
                .get("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CommentResponseDTO.class);

        // then
        assertThat(comments).hasSize(1);
    }

    @DisplayName("댓글 하나를 수정한다.")
    @Test
    void updateComment() {
        // given
        // 게시판 생성
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new BoardRequestDTO("공지사항"))
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.OK.value());

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // 댓글 생성
        CommentResponseDTO comment = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequestDTO("댓글", "작성자1", 1L))
                .when()
                .post("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CommentResponseDTO.class);

        // when
        // 댓글 수정
        CommentResponseDTO updateComment = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new CommentRequestDTO("수정 댓글", "작성자1", 1L))
                .when()
                .put("/api/comments/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CommentResponseDTO.class);

        // then
        assertThat(updateComment.content()).isEqualTo("수정 댓글");
    }

    @DisplayName("댓글 하나를 삭제한다.")
    @Test
    void deleteComment() {
        // given
        // 게시판 생성
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new BoardRequestDTO("공지사항"))
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.OK.value());

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // 댓글 생성
        CommentResponseDTO comment = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequestDTO("댓글", "작성자1", 1L))
                .when()
                .post("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CommentResponseDTO.class);

        // when
        RestAssured
                .given()
                .when()
                .delete("/api/comments/1")
                .then()
                .statusCode(HttpStatus.OK.value());

        // then
        List<CommentResponseDTO> comments = RestAssured
                .given().log().all()
                .queryParam("postId", 1L)
                .when()
                .get("/api/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CommentResponseDTO.class);

        // then
        assertThat(comments).hasSize(0);
    }
}
