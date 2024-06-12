package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.domain.QPost;
import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;

import static com.example.yeogiserver.post.domain.QPost.post;

@Getter
public enum SortCondition {
    LIKES() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost post) {
            return new OrderSpecifier<?>[] { post.postLikeList.size().desc() , post.createdAt.desc()};
        }
    },
    VIEWS() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost post) {
            return new OrderSpecifier<?>[] { post.viewCount.desc(), post.createdAt.desc() };
        }
    },
    RECENT() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost post) {
            return new OrderSpecifier<?>[] { post.createdAt.desc() };
        }
    };

    public abstract OrderSpecifier<?>[] getSpecifier(QPost post);
}