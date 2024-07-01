package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.yeogiserver.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class QueryDslPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BooleanExpression filterByTheme(Theme theme){
        if (Objects.isNull(theme)){
            return null;
        }

        return post.postThemeList.any().theme.eq(theme);
    }

    public List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition, Theme theme){
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.author)
                .where(postSearchType.getBooleanExpression(searchString, post), this.filterByTheme(theme))
                .orderBy(postSortCondition.getSpecifier(post))
                .fetch();
    }
}
