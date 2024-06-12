package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.domain.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Objects;

public enum SearchType {
    CONTENT {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost post) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            BooleanExpression titleContains = post.title.contains(searchString);
            BooleanExpression contentContains = post.content.contains(searchString);
            return titleContains.or(contentContains);
        }
    },
    NICKNAME {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost post) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            return post.author.nickname.contains(searchString);
        }
    },
    REGION {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost post) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            BooleanExpression continentContains = post.continent.contains(searchString);
            BooleanExpression countryContains = post.country.contains(searchString);
            return continentContains.or(countryContains);
        }
    };

    public abstract BooleanExpression getBooleanExpression(String searchString, QPost post);
}