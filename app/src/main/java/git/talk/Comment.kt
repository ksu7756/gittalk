package git.talk

class Comment{

    //댓글의 아이디
    var commentId = ""

    //댓글의 대상이 되는 글의 아이디
    var postId = ""

    //댓글작성자의 아이디
    var writeId = ""

    //댓글내용
    var message = ""

    //작성시간
    var writeTime:Any = Any()

    //배경 이미지
    var bgUri = ""

}