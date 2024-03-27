package seugi.server.global.exception

enum class CustomErrorCode (
    val code: Int,
    val msg: String
) {

    // 회원가입
    MEMBER_ALREADY_EXIST(400, "이메일 중복됨"),

    // JWT
    JWT_TOKEN_EXPIRED(400, "토큰 만료됨"),
    JWT_MEMBER_NOT_MATCH(400, "비밀번호가 일치하지 않음")

}