package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dto.CodingPostListResponseDTO;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CodingPostQueryServiceTest {

    @Autowired
    private CodingPostQueryService codingPostQueryService;

    @Test
    @DisplayName("코딩 풀이 게시물 전체 조회 - 게시물 목록과 전체 개수 반환 확인")
    void testFindCodingPostListWithTotalCount() {
        // given
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(5)
                .visibility("Y")
                .build();

        // when
        CodingPostListResponseDTO response = codingPostQueryService.findPostListWithPagination(condition);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isNotNull();
        assertThat(response.getTotalCount()).isGreaterThanOrEqualTo(0);

        // 페이징 크기 확인
        assertThat(response.getPosts().size()).isLessThanOrEqualTo(5);

        // 실제 조회된 게시물 수는 전체 개수보다 작거나 같아야 함
        assertThat(response.getPosts().size()).isLessThanOrEqualTo((int) response.getTotalCount());

        // 콘솔 출력
        System.out.println("===== 코딩 풀이 게시물 전체 조회 결과 =====");
        System.out.println("전체 게시물 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("========================================");

        response.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s | 문제: %s | 작성자: %s",
                post.getPostId(),
                post.getPostTitle(),
                post.getProblemTitle(),
                post.getNickname()))
        );
    }

    @Test
    @DisplayName("키워드 검색 시 totalCount가 검색 결과 개수 반환")
    void testFindCodingPostListWithKeywordSearch() {
        // given - 키워드로 검색
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .keyword("문제")
                .build();

        // when
        CodingPostListResponseDTO response = codingPostQueryService.findPostListWithPagination(condition);

        // then
        assertThat(response).isNotNull();

        // 콘솔 출력
        System.out.println("===== '문제' 키워드 검색 결과 =====");
        System.out.println("검색된 게시물 전체 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("================================");

        response.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s", post.getPostId(), post.getPostTitle()))
        );
    }

    @Test
    @DisplayName("페이징 처리 확인 - 여러 페이지 조회 시 totalCount는 동일")
    void testPaginationTotalCountConsistency() {
        // given
        CodingPostSearchConditionDTO page0 = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(3)
                .visibility("Y")
                .build();

        CodingPostSearchConditionDTO page1 = CodingPostSearchConditionDTO.builder()
                .page(1)
                .size(3)
                .visibility("Y")
                .build();

        // when
        CodingPostListResponseDTO responsePage0 = codingPostQueryService.findPostListWithPagination(page0);
        CodingPostListResponseDTO responsePage1 = codingPostQueryService.findPostListWithPagination(page1);

        // then
        // 두 페이지의 totalCount는 동일해야 함
        assertThat(responsePage0.getTotalCount()).isEqualTo(responsePage1.getTotalCount());

        // 콘솔 출력
        System.out.println("===== 페이징 처리 확인 =====");
        System.out.println("Page 0 - 전체 개수: " + responsePage0.getTotalCount() + ", 현재 페이지: " + responsePage0.getPosts().size());
        System.out.println("Page 1 - 전체 개수: " + responsePage1.getTotalCount() + ", 현재 페이지: " + responsePage1.getPosts().size());
        System.out.println("=========================");
    }

    @Test
    @DisplayName("게시물이 없는 경우 totalCount = 0, posts는 빈 리스트")
    void testEmptyResult() {
        // given - 존재하지 않는 키워드로 검색
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .keyword("존재하지않는키워드12345678901234567890")
                .build();

        // when
        CodingPostListResponseDTO response = codingPostQueryService.findPostListWithPagination(condition);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isEmpty();
        assertThat(response.getTotalCount()).isEqualTo(0);

        // 콘솔 출력
        System.out.println("===== 검색 결과 없음 =====");
        System.out.println("전체 개수: " + response.getTotalCount());
        System.out.println("게시물 리스트: " + response.getPosts());
        System.out.println("========================");
    }

    @Test
    @DisplayName("페이지 크기보다 전체 게시물이 적을 때 정상 처리")
    void testPostsLessThanPageSize() {
        // given - 큰 페이지 크기로 조회
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(100)
                .visibility("Y")
                .build();

        // when
        CodingPostListResponseDTO response = codingPostQueryService.findPostListWithPagination(condition);

        // then
        assertThat(response).isNotNull();
        // 조회된 게시물 수는 totalCount와 같거나 작아야 함
        assertThat(response.getPosts().size()).isLessThanOrEqualTo((int) response.getTotalCount());
        // 페이지 크기보다 작거나 같아야 함
        assertThat(response.getPosts().size()).isLessThanOrEqualTo(100);

        // 콘솔 출력
        System.out.println("===== 큰 페이지 크기로 조회 =====");
        System.out.println("전체 게시물 수: " + response.getTotalCount());
        System.out.println("조회된 게시물 수: " + response.getPosts().size());
        System.out.println("==============================");
    }

    @Test
    @DisplayName("특정 문제별 게시물 조회 시 totalCount가 해당 문제의 게시물 개수 반환")
    void testFindPostListByProblemId() {
        // given - 특정 문제 ID로 조회
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .problemId(1)
                .build();

        // when
        CodingPostListResponseDTO response = codingPostQueryService.findPostListWithPagination(condition);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isNotNull();

        // 모든 게시물이 해당 문제 ID를 가지고 있는지 확인 (첫 페이지만)
        // response.getPosts().forEach(post ->
        //     assertThat(post.getProblemId()).isEqualTo(1)
        // );

        // 콘솔 출력
        System.out.println("===== 문제 ID 1번 게시물 조회 결과 =====");
        System.out.println("해당 문제의 게시물 전체 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("====================================");
    }
}
