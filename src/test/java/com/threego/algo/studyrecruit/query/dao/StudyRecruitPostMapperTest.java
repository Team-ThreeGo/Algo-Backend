package com.threego.algo.studyrecruit.query.dao;

import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitPostDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitSearchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyRecruitPostMapperTest {

    @Autowired
    private StudyRecruitPostMapper studyRecruitPostMapper;
    private RecruitStatus recruitStatus;

    @Test
    @DisplayName("페이징 처리 - 첫 페이지 조회 (size=5)")
    void testPaginationFirstPage() {
        // given
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(0)
                .size(5)
                .build();

        // when
        List<StudyRecruitPostDTO> result = studyRecruitPostMapper.selectStudyRecruitList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("첫 페이지 결과 개수: " + result.size());
        result.forEach(post -> System.out.println("Post ID: " + post.getId() + ", Title: " + post.getTitle()));
    }

    @Test
    @DisplayName("페이징 처리 - 두 번째 페이지 조회 (page=1, size=5)")
    void testPaginationSecondPage() {
        // given
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(1)
                .size(5)
                .build();

        // when
        List<StudyRecruitPostDTO> result = studyRecruitPostMapper.selectStudyRecruitList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("두 번째 페이지 결과 개수: " + result.size());
        result.forEach(post -> System.out.println("Post ID: " + post.getId() + ", Title: " + post.getTitle()));
    }

    @Test
    @DisplayName("페이징 처리 - 기본값 테스트 (page, size 미지정)")
    void testPaginationDefaultValues() {
        // given
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .build();

        // when
        List<StudyRecruitPostDTO> result = studyRecruitPostMapper.selectStudyRecruitList(searchDto);

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
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(0)
                .size(3)
                .status("OPEN")
                .build();

        // when
        List<StudyRecruitPostDTO> result = studyRecruitPostMapper.selectStudyRecruitList(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(3);
        result.forEach(post -> {
            assertThat(post.getStatus().name()).isEqualTo(searchDto.getStatus());
            System.out.println("Post ID: " + post.getId() + ", Status: " + post.getStatus());
        });
    }

    @Test
    @DisplayName("페이징 offset 계산 확인")
    void testOffsetCalculation() {
        // given
        StudyRecruitSearchDTO page0 = StudyRecruitSearchDTO.builder()
                .page(0)
                .size(2)
                .build();

        StudyRecruitSearchDTO page1 = StudyRecruitSearchDTO.builder()
                .page(1)
                .size(2)
                .build();

        // when
        List<StudyRecruitPostDTO> resultPage0 = studyRecruitPostMapper.selectStudyRecruitList(page0);
        List<StudyRecruitPostDTO> resultPage1 = studyRecruitPostMapper.selectStudyRecruitList(page1);

        // then
        System.out.println("=== Page 0 ===");
        resultPage0.forEach(post -> System.out.println("Post ID: " + post.getId()));

        System.out.println("=== Page 1 ===");
        resultPage1.forEach(post -> System.out.println("Post ID: " + post.getId()));

        // 두 페이지의 결과가 겹치지 않아야 함
        if (!resultPage0.isEmpty() && !resultPage1.isEmpty()) {
            assertThat(resultPage0.get(0).getId()).isNotEqualTo(resultPage1.get(0).getId());
        }
    }

    @Test
    @DisplayName("PageableDTO 상속 확인 - getOffset() 메서드 동작")
    void testPageableDTOInheritance() {
        // given
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(2)
                .size(5)
                .build();

        // when
        int offset = searchDto.getOffset();

        // then
        assertThat(offset).isEqualTo(10); // page(2) * size(5) = 10
        System.out.println("Calculated offset: " + offset);
    }
}
