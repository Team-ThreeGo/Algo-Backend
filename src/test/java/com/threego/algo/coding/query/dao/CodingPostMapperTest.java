package com.threego.algo.coding.query.dao;

import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CodingPostMapperTest {

    @Autowired
    private CodingPostMapper codingPostMapper;

    @Test
    @DisplayName("페이징 처리 - 첫 페이지 조회 (size=5)")
    void testPaginationFirstPage() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(5)
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectPostList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("첫 페이지 결과 개수: " + result.size());
        result.forEach(post -> System.out.println("Post ID: " + post.getPostId() + ", Title: " + post.getPostTitle()));
    }

    @Test
    @DisplayName("페이징 처리 - 두 번째 페이지 조회 (page=1, size=5)")
    void testPaginationSecondPage() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .page(1)
                .size(5)
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectPostList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("두 번째 페이지 결과 개수: " + result.size());
        result.forEach(post -> System.out.println("Post ID: " + post.getPostId() + ", Title: " + post.getPostTitle()));
    }

    @Test
    @DisplayName("페이징 처리 - 기본값 테스트 (page, size 미지정)")
    void testPaginationDefaultValues() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectPostList(searchDto);

        // then
        assertThat(result).isNotNull();
        // PageableDTO의 기본값: page=0, size=10
        assertThat(result.size()).isLessThanOrEqualTo(10);
        System.out.println("기본값 페이지 결과 개수: " + result.size());
    }

    @Test
    @DisplayName("페이징 + 검색 조건 조합 테스트")
    void testPaginationWithSearchCondition() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(3)
                .keyword("알고리즘")
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectPostList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(3);
        System.out.println("검색 결과 개수: " + result.size());
        result.forEach(post -> {
            System.out.println("Post ID: " + post.getPostId() + ", Title: " + post.getPostTitle());
        });
    }

    @Test
    @DisplayName("PageableDTO 상속 확인 - getOffset() 메서드 동작")
    void testPageableDTOInheritance() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .page(2)
                .size(5)
                .build();

        // when
        int offset = searchDto.getOffset();

        // then
        assertThat(offset).isEqualTo(10); // page(2) * size(5) = 10
        System.out.println("Calculated offset: " + offset);
    }

    @Test
    @DisplayName("문제별 게시물 페이징 조회")
    void testProblemPostsPagination() {
        // given
        int problemId = 1;
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .problemId(problemId)
                .page(0)
                .size(5)
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectPostListByProblemId(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("문제별 게시물 개수: " + result.size());
        result.forEach(post -> {
            System.out.println("Post ID: " + post.getPostId() + ", Problem: " + post.getProblemTitle());
        });
    }

    @Test
    @DisplayName("관리자용 게시물 페이징 조회")
    void testAdminPostsPagination() {
        // given
        CodingPostSearchConditionDTO searchDto = CodingPostSearchConditionDTO.builder()
                .visibility("N")
                .page(0)
                .size(5)
                .build();

        // when
        List<CodingPostSummaryDTO> result = codingPostMapper.selectAdminPostList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("관리자 조회 게시물 개수: " + result.size());
        result.forEach(post -> {
            System.out.println("Post ID: " + post.getPostId() + ", Visibility: " + post.getVisibility());
        });
    }
}
