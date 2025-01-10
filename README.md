# 게시판 API Specification

---

# 📍 게시판

## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/boards`
- **Example Endpoint**: `https://localhost:8080/boards`
- **Request Parameters**:
    - **Body Parameters**:
        - `boardName` (String): 게시판 이름

---

## 🔍 **조회 (하나의 게시판)**
- **Method**: `GET`
- **Path**: `/boards/{boardId}`
- **Example Endpoint**: `https://localhost:8080/boards/{boardId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `boardId` (Number): 게시판 ID
- **Response Message**:
    - `message` (String)
    - `data`:
        - `boardId` (Number): 게시판 ID
        - `boardName` (String): 게시판 이름

---

## 🔍 **조회 (전체 게시판)**
- **Method**: `GET`
- **Path**: `/boards`
- **Example Endpoint**: `https://localhost:8080/boards`

---

## ✏️ **수정**
- **Method**: `PUT`
- **Path**: `/boards/{boardId}`
- **Example Endpoint**: `https://localhost:8080/boards/{boardId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `boardId` (Number): 게시판 ID
    - **Body Parameters**:
        - `boardName` (String): 게시판 이름

---

## ❌ **삭제**
- **Method**: `DELETE`
- **Path**: `/boards/{boardId}`
- **Example Endpoint**: `https://localhost:8080/boards/{boardId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `boardId` (Number): 게시판 ID
- **Response Message**:
    - `message` (String)

---

# 📝 게시글

## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/posts`
- **Example Endpoint**: `https://localhost:8080/posts`
- **Request Parameters**:
    - **Body Parameters**:
        - `title` (String): 게시글 제목
        - `content` (String): 게시글 내용
        - `boardId` (Number): 게시판 ID

---

## 🔍 **조회 (하나의 게시글)**
- **Method**: `GET`
- **Path**: `/posts/{postId}`
- **Example Endpoint**: `https://localhost:8080/posts/{postId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `postId` (Number): 게시글 ID
- **Response Message**:
    - `message` (String)
    - `data`:
        - `postId` (Number): 게시글 ID
        - `title` (String): 게시글 제목
        - `content` (String): 게시글 내용
        - `boardId` (Number): 게시판 ID
        - `createdAt` (String): 생성 시간

---

## 🔍 **조회 (전체 게시글)**
- **Method**: `GET`
- **Path**: `/posts`
- **Example Endpoint**: `https://localhost:8080/posts?boardId=1`
- **Request Parameters**:
    - **Query String Parameter**:
        - `boardId` (Number): 게시판 ID
- **Response Message**:
    - `message` (String)
    - `data`:
        - **posts** (Array):
            - `postId` (Number): 게시글 ID
            - `title` (String): 게시글 제목
            - `content` (String): 게시글 내용
            - `boardId` (Number): 게시판 ID
            - `createdAt` (String): 생성 시간

---

## ✏️ **수정**
- **Method**: `PUT`
- **Path**: `/posts/{postId}`
- **Example Endpoint**: `https://localhost:8080/posts/{postId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `postId` (Number): 게시글 ID
    - **Body Parameters**:
        - `title` (String): 게시글 제목
        - `content` (String): 게시글 내용

---

## ❌ **삭제**
- **Method**: `DELETE`
- **Path**: `/posts/{postId}`
- **Example Endpoint**: `https://localhost:8080/posts/{postId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `postId` (Number): 게시글 ID
- **Response Message**:
    - `message` (String)

---

# 💬 댓글

## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/comments`
- **Example Endpoint**: `https://localhost:8080/comments`
- **Request Parameters**:
    - **Body Parameters**:
        - `content` (String): 댓글 내용
        - `postId` (Number): 게시글 ID
        - `author` (String): 작성자

---

## 🔍 **조회 (전체 댓글)**
- **Method**: `GET`
- **Path**: `/comments`
- **Example Endpoint**: `https://localhost:8080/comments`
- **Response Message**:
    - `message` (String)
    - `data`:
        - **comments** (Array):
            - `postId` (Number): 게시글 ID
            - `content` (String): 댓글 내용
            - `author` (String): 작성자
            - `createdAt` (String): 생성 시간

---

## ✏️ **수정**
- **Method**: `PUT`
- **Path**: `/comments/{commentId}`
- **Example Endpoint**: `https://localhost:8080/comments/{commentId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `commentId` (Number): 댓글 ID
    - **Body Parameters**:
        - `content` (String): 댓글 내용

---

## ❌ **삭제**
- **Method**: `DELETE`
- **Path**: `/comments/{commentId}`
- **Example Endpoint**: `https://localhost:8080/comments/{commentId}`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `commentId` (Number): 댓글 ID
- **Response Message**:
    - `message` (String)

