package com.threego.algo.member.query.controller;

import com.threego.algo.member.query.dto.MemberDetailResponseDTO;
import com.threego.algo.member.query.service.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Member - Member Query",
        description = "회원용 회원 API (Query)"
)
@RestController
@RequestMapping("/member")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;

    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Operation(
            summary = "회원 정보 조회",
            description = "id를 통해 회원을 검색할 수 있습니다."
    )
    @GetMapping("/{id}/info")
    public ResponseEntity<MemberDetailResponseDTO> findMemberById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(memberQueryService.findMemberById(id));
    }
}
