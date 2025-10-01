package com.threego.algo.member.command.application.controller;

import com.threego.algo.member.command.application.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Member - Member Command",
        description = "회원용 회원 관리 API (Command)"
)
@RestController
@RequestMapping("/member")
public class MemberCommandController {
    private MemberCommandService memberService;

    @Autowired
    public void setMemberCommandService(MemberCommandService memberCommandService) {
        this.memberService = memberCommandService;
    }

    @Operation(
            summary = "회원 정보 변경",
            description = "회원이 닉네임을 변경할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임으로 닉네임 변경 실패")
    })
    @PutMapping("/{memberId}/nickname")
    public ResponseEntity<String> updateMemberInfo(
            @PathVariable int memberId,
            @RequestParam String newNickname
    ) {
        try {
            memberService.updateMemberInfo(memberId, newNickname);
            return ResponseEntity.ok("닉네임 변경 성공");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임이 이미 존재합니다.");
        }
    }


    @Operation(
            summary = "회원 출석",
            description = "회원이 출석 버튼을 누를 시 출석 일수가 증가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "출석 성공"),
            @ApiResponse(responseCode = "409", description = "오늘 날짜의 출석 데이터 존재")
    })
    @PostMapping("/{memberId}/attendance")
    public ResponseEntity<String> createMemberAttendance(@PathVariable int memberId) {
        try {
            String date = memberService.createAttendance(memberId);
            return ResponseEntity.ok(date + " 출석 완료");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 출석하였습니다.");
        }
    }
}
