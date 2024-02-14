package com.sparta.bubbleclub.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(
    password: String,
    email: String,
    nickname: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var long: Long? = null
        private set

    @Column(name = "email")
    var email: String = email
        private set

    @Column(name = "password")
    var password: String = password
        private set

    @Column(name = "nickname")
    var nickname: String = nickname
        private set

}



