package com.example.yeogiserver.reply.domain;

import jakarta.persistence.*;

public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String content;

    //TODO. POST_ID

}
