package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.post.presentation.SearchType;
import com.example.yeogiserver.post.presentation.SortCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.yeogiserver.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class QueryDslPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO : 페이징 처리
    public List<Post> findPostListBySearchTypeAndSortCondition(SearchType searchType, String searchString, SortCondition sortCondition){
        return jpaQueryFactory.selectFrom(post)
                .where(searchType.getBooleanExpression(searchString, post))
                .orderBy(sortCondition.getSpecifier(post))
                .fetch();
    }
}
