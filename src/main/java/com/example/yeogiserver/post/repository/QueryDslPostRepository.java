package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.querydsl.core.BooleanBuilder;
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

    public BooleanExpression filterByTheme(List<Theme> themes){
        if (Objects.isNull(themes) || themes.isEmpty()){
            return null;
        }
        return post.postThemeList.any().theme.in(themes);
    }

    public BooleanExpression filterByCountry(String continent){
        if (Objects.isNull(continent) || continent.isEmpty()){
            return null;
        }

        return post.continent.like(continent);
    }
    public BooleanExpression filterByCountryList(List<String> countryList ) {
        if (Objects.isNull(countryList) || countryList.isEmpty()){
            return null;
        }

        return post.country.in(countryList);
    }

    // TODO : LIKE, COMMENT 의 카운트를 한방 쿼리로 변경한다.
    public List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition, String continent, List<Theme> themes){
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.author)
                .where(postSearchType.getBooleanExpression(searchString, post), this.filterByCountry(continent), this.filterByTheme(themes))
                .orderBy(postSortCondition.getSpecifier(post))
                .fetch();
    }

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
    public List<Post> findByRecommandThemeOrCountry(List<Theme> themeList,List<String> countryList ) {

        BooleanBuilder booleanBuilder  = new BooleanBuilder();

        booleanBuilder.or(this.filterByTheme(themeList));
        booleanBuilder.or(this.filterByCountryList(countryList));

        return jpaQueryFactory.selectFrom(post)
                .where(booleanBuilder)
                .offset(0)
                .limit(12)
                .fetch();

    }
}
