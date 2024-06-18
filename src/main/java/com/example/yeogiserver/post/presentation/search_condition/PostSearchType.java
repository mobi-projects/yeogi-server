package com.example.yeogiserver.post.presentation.search_condition;

import com.example.yeogiserver.base.presentation.SearchType;
import com.example.yeogiserver.post.domain.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Objects;

public enum PostSearchType implements SearchType<QPost> {
    CONTENT {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost targetQEntity) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            BooleanExpression titleContains = targetQEntity.title.contains(searchString);
            BooleanExpression contentContains = targetQEntity.content.contains(searchString);
            return titleContains.or(contentContains);
        }
    },
    NICKNAME {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost targetQEntity) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            return targetQEntity.author.nickname.contains(searchString);
        }
    },
    REGION {
        @Override
        public BooleanExpression getBooleanExpression(String searchString, QPost targetQEntity) {
            if (Objects.isNull(searchString) || searchString.isEmpty()) {
                return null;
            }
            BooleanExpression continentContains = targetQEntity.continent.contains(searchString);
            BooleanExpression countryContains = targetQEntity.country.contains(searchString);
            return continentContains.or(countryContains);
        }
    };
}