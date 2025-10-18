package com.threego.algo.career.query.service;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dto.CareerPostListResponseDto;
import com.threego.algo.career.query.dto.CareerPostSearchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CareerQueryServiceTest {

    @Autowired
    private CareerQueryService careerQueryService;

    @Test
    @DisplayName("기업별 정보 공유 게시물 전체 조회 - 게시물 목록과 전체 개수 반환 확인")
    void testFindCareerPostListWithTotalCount() {
        // given
        CareerPostSearchDTO searchDTO = CareerPostSearchDTO.builder()
                .page(0)
                .size(5)
                .visibility("Y")
                .build();

        // when
        CareerPostListResponseDto response = careerQueryService.findPostListWithPagination(searchDTO);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isNotNull();
        assertThat(response.getTotalCount()).isGreaterThanOrEqualTo(0);

        // 페이징 크기 확인
        assertThat(response.getPosts().size()).isLessThanOrEqualTo(5);

        // 실제 조회된 게시물 수는 전체 개수보다 작거나 같아야 함
        assertThat(response.getPosts().size()).isLessThanOrEqualTo((int) response.getTotalCount());

        // 콘솔 출력
        System.out.println("===== 기업별 정보 공유 게시물 전체 조회 결과 =====");
        System.out.println("전체 게시물 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("============================================");

        response.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s | 상태: %s | 작성자: %s",
                post.getId(),
                post.getTitle(),
                post.getStatus(),
                post.getNickname()))
        );
    }

    @Test
    @DisplayName("상태 필터링 적용 시 totalCount가 필터링된 개수 반환")
    void testFindCareerPostListWithStatusFilter() {
        // given - APPROVED 상태만 조회
        CareerPostSearchDTO searchDTO = CareerPostSearchDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .status(Status.APPROVED)
                .build();

        // when
        CareerPostListResponseDto response = careerQueryService.findPostListWithPagination(searchDTO);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isNotNull();

        // 모든 게시물이 APPROVED 상태인지 확인
        response.getPosts().forEach(post ->
            assertThat(post.getStatus()).isEqualTo("APPROVED")
        );

        // 콘솔 출력
        System.out.println("===== APPROVED 상태 게시물 조회 결과 =====");
        System.out.println("APPROVED 상태 게시물 전체 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("========================================");
    }

    @Test
    @DisplayName("키워드 검색 시 totalCount가 검색 결과 개수 반환")
    void testFindCareerPostListWithKeywordSearch() {
        // given - 키워드로 검색
        CareerPostSearchDTO searchDTO = CareerPostSearchDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .keyword("코테")
                .build();

        // when
        CareerPostListResponseDto response = careerQueryService.findPostListWithPagination(searchDTO);

        // then
        assertThat(response).isNotNull();

        // 콘솔 출력
        System.out.println("===== '코테' 키워드 검색 결과 =====");
        System.out.println("검색된 게시물 전체 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("=================================");

        response.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s", post.getId(), post.getTitle()))
        );
    }

    @Test
    @DisplayName("페이징 처리 확인 - 여러 페이지 조회 시 totalCount는 동일")
    void testPaginationTotalCountConsistency() {
        // given
        CareerPostSearchDTO page0 = CareerPostSearchDTO.builder()
                .page(0)
                .size(3)
                .visibility("Y")
                .build();

        CareerPostSearchDTO page1 = CareerPostSearchDTO.builder()
                .page(1)
                .size(3)
                .visibility("Y")
                .build();

        // when
        CareerPostListResponseDto responsePage0 = careerQueryService.findPostListWithPagination(page0);
        CareerPostListResponseDto responsePage1 = careerQueryService.findPostListWithPagination(page1);

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
        CareerPostSearchDTO searchDTO = CareerPostSearchDTO.builder()
                .page(0)
                .size(10)
                .visibility("Y")
                .keyword("존재하지않는키워드12345678901234567890")
                .build();

        // when
        CareerPostListResponseDto response = careerQueryService.findPostListWithPagination(searchDTO);

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
        CareerPostSearchDTO searchDTO = CareerPostSearchDTO.builder()
                .page(0)
                .size(100)
                .visibility("Y")
                .build();

        // when
        CareerPostListResponseDto response = careerQueryService.findPostListWithPagination(searchDTO);

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
}
