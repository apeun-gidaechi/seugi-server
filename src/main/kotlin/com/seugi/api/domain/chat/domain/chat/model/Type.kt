package com.seugi.api.domain.chat.domain.chat.model

enum class Type {
    MESSAGE, IMG, FILE, ENTER, LEFT, TRANSFER_ADMIN, SUB, UNSUB, DELETE_MESSAGE, ADD_EMOJI, REMOVE_EMOJI, BOT
}

// 메시지, 이미지, 파일, 입장(초대), 퇴장(강퇴), 방 접속, 방 접속 해제, 메시지 삭제, 이모지 추가, 이모지 삭제, 봇