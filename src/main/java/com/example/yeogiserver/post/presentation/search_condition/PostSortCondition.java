package com.example.yeogiserver.post.presentation.search_condition;

import com.example.yeogiserver.base.presentation.SortCondition;
import com.example.yeogiserver.post.domain.QPost;
import com.querydsl.core.types.OrderSpecifier;


public enum PostSortCondition implements SortCondition<QPost> {
    LIKES() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost qPost) {
            return new OrderSpecifier<?>[] { qPost.postLikeList.size().desc() , qPost.createdAt.desc()};
        }
    },
    VIEWS() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost qPost) {
            return new OrderSpecifier<?>[] { qPost.viewCount.desc(), qPost.createdAt.desc() };
        }
    },
    RECENT() {
        @Override
        public OrderSpecifier<?>[] getSpecifier(QPost qPost) {
            return new OrderSpecifier<?>[] { qPost.createdAt.desc() };
        }
    };
}