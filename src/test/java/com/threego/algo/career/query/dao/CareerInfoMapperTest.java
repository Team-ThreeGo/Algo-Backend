package com.threego.algo.career.query.dao;

import com.threego.algo.career.query.dto.CareerPostSearchDTO;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CareerInfoMapperTest {

    @Autowired
    private CareerInfoMapper careerInfoMapper;

    @Test
    @DisplayName("페이징 처리 - 첫 페이지 조회 (size=5)")
    void testPaginationFirstPage() {
        // given
        CareerPostSearchDTO searchDto = CareerPostSearchDTO.builder()
                .visibility("Y")
                .page(0)
                .size(5)
                .build();

        // when
        List<PostSummaryResponseDto> result = careerInfoMapper.selectPostListWithPagination(searchDto);

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
        CareerPostSearchDTO searchDto = CareerPostSearchDTO.builder()
                .visibility("Y")
                .page(1)
                .size(5)
                .build();

        // when
        List<PostSummaryResponseDto> result = careerInfoMapper.selectPostListWithPagination(searchDto);

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
        CareerPostSearchDTO searchDto = CareerPostSearchDTO.builder()
                .visibility("Y")
                .build();

        // when
        List<PostSummaryResponseDto> result = careerInfoMapper.selectPostListWithPagination(searchDto);

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
        CareerPostSearchDTO searchDto = CareerPostSearchDTO.builder()
                .visibility("Y")
                .page(0)
                .size(3)
                .keyword("기업")
                .build();

        // when
        List<PostSummaryResponseDto> result = careerInfoMapper.selectPostListWithPagination(searchDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(3);
        System.out.println("검색 결과 개수: " + result.size());
        result.forEach(post -> {
            System.out.println("Post ID: " + post.getId() + ", Title: " + post.getTitle());
        });
    }

    @Test
    @DisplayName("PageableDTO 상속 확인 - getOffset() 메서드 동작")
    void testPageableDTOInheritance() {
        // given
        CareerPostSearchDTO searchDto = CareerPostSearchDTO.builder()
                .visibility("Y")
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
