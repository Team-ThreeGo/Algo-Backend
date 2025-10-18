package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostListResponseDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyPostQueryServiceTest {

    @Autowired
    private StudyPostQueryService studyPostQueryService;

    @Test
    @DisplayName("스터디 게시물 전체 조회 - 게시물 목록과 전체 개수 반환 확인")
    void testFindAllStudyPostsWithTotalCount() {
        // given - 실제 DB에 존재하는 studyId를 사용해야 합니다
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)  // 실제 존재하는 스터디 ID
                .page(0)
                .size(5)
                .build();

        // when
        StudyPostListResponseDTO response = studyPostQueryService.findAllStudyPosts(searchDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isNotNull();
        assertThat(response.getTotalCount()).isGreaterThanOrEqualTo(0);

        // 페이징 크기 확인
        assertThat(response.getPosts().size()).isLessThanOrEqualTo(5);

        // 콘솔 출력
        System.out.println("===== 스터디 게시물 전체 조회 결과 =====");
        System.out.println("전체 게시물 수: " + response.getTotalCount());
        System.out.println("현재 페이지 게시물 수: " + response.getPosts().size());
        System.out.println("=====================================");

        response.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s | 작성자: %s",
                post.getId(),
                post.getTitle(),
                post.getMemberNickname()))
        );
    }

    @Test
    @DisplayName("페이징 처리 확인 - 여러 페이지 조회 시 totalCount는 동일")
    void testPaginationTotalCountConsistency() {
        // given
        StudyPostSearchDTO page0 = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(0)
                .size(3)
                .build();

        StudyPostSearchDTO page1 = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(1)
                .size(3)
                .build();

        // when
        StudyPostListResponseDTO responsePage0 = studyPostQueryService.findAllStudyPosts(page0);
        StudyPostListResponseDTO responsePage1 = studyPostQueryService.findAllStudyPosts(page1);

        // then
        // 두 페이지의 totalCount는 동일해야 함
        assertThat(responsePage0.getTotalCount()).isEqualTo(responsePage1.getTotalCount());

        // 콘솔 출력
        System.out.println("===== 페이징 처리 확인 =====");
        System.out.println("Page 0 - 전체 개수: " + responsePage0.getTotalCount() + ", 현재 페이지: " + responsePage0.getPosts().size());
        System.out.println("Page 1 - 전체 개수: " + responsePage1.getTotalCount() + ", 현재 페이지: " + responsePage1.getPosts().size());
        System.out.println("=========================");

        // Page 0 게시물 목록
        System.out.println("=== Page 0 게시물 ===");
        responsePage0.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s", post.getId(), post.getTitle()))
        );

        // Page 1 게시물 목록
        System.out.println("=== Page 1 게시물 ===");
        responsePage1.getPosts().forEach(post ->
            System.out.println(String.format("ID: %d | 제목: %s", post.getId(), post.getTitle()))
        );
    }

    @Test
    @DisplayName("게시물이 없는 스터디 조회 시 totalCount = 0, posts는 빈 리스트")
    void testEmptyResult() {
        // given - 존재하지 않는 studyId
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(99999)  // 존재하지 않는 스터디 ID
                .page(0)
                .size(10)
                .build();

        // when
        StudyPostListResponseDTO response = studyPostQueryService.findAllStudyPosts(searchDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPosts()).isEmpty();
        assertThat(response.getTotalCount()).isEqualTo(0);

        // 콘솔 출력
        System.out.println("===== 게시물 없음 =====");
        System.out.println("전체 개수: " + response.getTotalCount());
        System.out.println("게시물 리스트: " + response.getPosts());
        System.out.println("=====================");
    }

    @Test
    @DisplayName("스터디별 게시물 개수가 올바르게 카운트되는지 확인")
    void testCountByStudyId() {
        // given - 여러 스터디 ID로 조회
        StudyPostSearchDTO searchDto1 = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(0)
                .size(100)  // 전체 조회
                .build();

        StudyPostSearchDTO searchDto2 = StudyPostSearchDTO.builder()
                .studyId(2)
                .page(0)
                .size(100)  // 전체 조회
                .build();

        // when
        StudyPostListResponseDTO response1 = studyPostQueryService.findAllStudyPosts(searchDto1);
        StudyPostListResponseDTO response2 = studyPostQueryService.findAllStudyPosts(searchDto2);

        // then
        System.out.println("===== 스터디별 게시물 개수 =====");
        System.out.println("스터디 ID 1 - 전체 게시물 수: " + response1.getTotalCount());
        System.out.println("스터디 ID 1 - 실제 조회된 게시물 수: " + response1.getPosts().size());
        System.out.println("스터디 ID 2 - 전체 게시물 수: " + response2.getTotalCount());
        System.out.println("스터디 ID 2 - 실제 조회된 게시물 수: " + response2.getPosts().size());
        System.out.println("==============================");

        // totalCount와 실제 posts 개수가 일치해야 함 (size가 충분히 큰 경우)
        assertThat(response1.getTotalCount()).isEqualTo(response1.getPosts().size());
        assertThat(response2.getTotalCount()).isEqualTo(response2.getPosts().size());
    }

    @Test
    @DisplayName("VISIBILITY = 'Y'인 게시물만 카운트되는지 확인")
    void testOnlyVisiblePostsCounted() {
        // given
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(0)
                .size(10)
                .build();

        // when
        StudyPostListResponseDTO response = studyPostQueryService.findAllStudyPosts(searchDto);

        // then
        // 모든 게시물의 visibility가 'Y'여야 함
        response.getPosts().forEach(post -> {
            assertThat(post.getVisibility()).isEqualTo("Y");
        });

        System.out.println("===== VISIBILITY 확인 =====");
        System.out.println("조회된 게시물 수: " + response.getPosts().size());
        System.out.println("모든 게시물이 VISIBILITY='Y' 조건을 만족합니다.");
        System.out.println("==========================");
    }
}
