package com.threego.algo.study.query.dao;

import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyPostMapperTest {

    @Autowired
    private StudyPostMapper studyPostMapper;

    @Test
    @DisplayName("페이징 처리 - 첫 페이지 조회 (size=5)")
    void testPaginationFirstPage() {
        // given
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(0)
                .size(5)
                .build();

        // when
        List<StudyPostDTO> result = studyPostMapper.selectAllStudyPosts(searchDto);

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
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(1)
                .size(5)
                .build();

        // when
        List<StudyPostDTO> result = studyPostMapper.selectAllStudyPosts(searchDto);

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
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
                .build();

        // when
        List<StudyPostDTO> result = studyPostMapper.selectAllStudyPosts(searchDto);

        // then
        assertThat(result).isNotNull();
        // PageableDTO의 기본값: page=0, size=10
        assertThat(result.size()).isLessThanOrEqualTo(10);
        System.out.println("기본값 페이지 결과 개수: " + result.size());
    }

    @Test
    @DisplayName("PageableDTO 상속 확인 - getOffset() 메서드 동작")
    void testPageableDTOInheritance() {
        // given
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
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
    @DisplayName("숨김 처리된 게시물 페이징 조회")
    void testHiddenPostsPagination() {
        // given
        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(1)
                .page(0)
                .size(5)
                .build();

        // when
        List<StudyPostDTO> result = studyPostMapper.selectAllHiddenStudyPosts(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(5);
        System.out.println("숨김 처리된 게시물 개수: " + result.size());
        result.forEach(post -> {
            assertThat(post.getVisibility()).isEqualTo("N");
            System.out.println("Post ID: " + post.getId() + ", Visibility: " + post.getVisibility());
        });
    }
}
