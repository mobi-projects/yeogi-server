package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.yeogiserver.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class QueryDslPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition){
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.author)
                .where(postSearchType.getBooleanExpression(searchString, post))
                .orderBy(postSortCondition.getSpecifier(post))
                .fetch();
    }

    // TODO : 페이징 처리
}
