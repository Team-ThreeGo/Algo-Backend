package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.coding.command.domain.aggregate.CodingProblem;
import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.enums.RankName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRank;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

class CodingPostCommandServiceImplTest {

    @Mock
    private Member member;

    @Mock
    private CodingProblem problem;

    @Mock
    private CodingPost codingPost;

    @BeforeEach
    public void setUp() {
        MemberRank memberRank = new MemberRank();
        memberRank.setId(1);
        memberRank.setName(RankName.코뉴비);
        memberRank.setMinPoint(0);
        memberRank.setImageUrl("http://example.com/image.jpg");

        member = new Member("test@example.com", "password", "testUser", memberRank, DateTimeUtils.nowDateTime());
        problem = new CodingProblem(); // CodingProblem 클래스에 대한 적절한 초기화 필요
        codingPost = CodingPost.create(member, problem, "테스트 제목", "테스트 내용");
    }

    @DisplayName("코딩 게시물 생성 성공")
    @Test
    public void testcreatePost() {
        assertNotNull(codingPost);
        assertEquals("테스트 제목", codingPost.getTitle());
        assertEquals("테스트 내용", codingPost.getContent());
        assertEquals(0, codingPost.getCommentCount());
        assertEquals(0, codingPost.getLikeCount());
        assertEquals("Y", codingPost.getVisibility());
    }

    @DisplayName("코딩 게시물 수정 성공")
    @Test
    public void testUpdateCodingPost() {
        codingPost.update("업데이트된 제목", "업데이트된 내용");
        assertEquals("업데이트된 제목", codingPost.getTitle());
        assertEquals("업데이트된 내용", codingPost.getContent());
    }

    @DisplayName("코딩 게시물 삭제 성공")
    @Test
    public void testDeleteCodingPost() {
        codingPost.delete();
        assertEquals("N", codingPost.getVisibility());
    }
}