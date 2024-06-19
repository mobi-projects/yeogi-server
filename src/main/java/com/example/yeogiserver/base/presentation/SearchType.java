package com.example.yeogiserver.base.presentation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

@FunctionalInterface
public interface SearchType<T extends EntityPathBase<?>> {
    BooleanExpression getBooleanExpression(String searchString, T targetQEntity);
}
