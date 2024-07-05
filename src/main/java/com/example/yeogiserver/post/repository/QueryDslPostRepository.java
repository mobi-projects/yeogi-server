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
import static com.example.yeogiserver.post.domain.QPostTheme.postTheme;

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

//    public List<Post> findPopularPostListByTheme(List<Theme> themes){
//        return themes.stream()
//                .map(this::findPostByThemeOrderByLikesDesc)
//                .toList();
//
//    }

    public List<Post> findPostByThemeOrderByLikesDesc(Theme each) {

        List<Long> postIdList = jpaQueryFactory.select(post.id).from(postTheme)
                .where(postTheme.theme.eq(each))
                .fetch();

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.postThemeList).fetchJoin()
//                .leftJoin(post.postLikeList) // FetchJoin 불가능 (~ToMany 2개 이상 불가능)
                .where(post.id.in(postIdList))
//                .orderBy(post.postLikeList.size().desc())
                .orderBy(post.viewCount.desc(), post.createdAt.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }
}
