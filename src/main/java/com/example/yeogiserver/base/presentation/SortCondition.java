package com.example.yeogiserver.base.presentation;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;

@FunctionalInterface
public interface SortCondition<T extends EntityPathBase<?>> {

    OrderSpecifier<?>[] getSpecifier(T targetQEntity);

}
