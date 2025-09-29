package com.threego.algo.member.command.application.controller;

import com.threego.algo.member.command.domain.aggregate.enums.RoleName;
import com.threego.algo.member.command.domain.aggregate.enums.Status;
import com.threego.algo.member.command.application.service.AdminMemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Member - Admin Command",
        description = "관리자용 회원 관리 API (Command)"
)
@RestController
@RequestMapping("/admin/member")
public class AdminMemberCommandController {
    private final AdminMemberCommandService adminService;

    @Autowired
    public AdminMemberCommandController(AdminMemberCommandService adminMemberCommandService) {
        this.adminService = adminMemberCommandService;
    }

    @Operation(
            summary = "회원 상태 변경 (관리자)",
            description = "관리자가 회원의 상태를 ACTIVE, INACTIVE 또는 BLOCKED로 변경할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 상태 변경 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (관리자 아님)"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @PutMapping("/{memberId}/status")
    public ResponseEntity<String> updateMemberStatus(
            @PathVariable int memberId,
            @RequestParam Status status
    ) {
        adminService.updateMemberStatus(memberId, status);
        return ResponseEntity.ok("회원id " + memberId + "의 상태를 " + status + "로 변경 성공");
    }

    @Operation(
            summary = "회원 권한 변경 (관리자)",
            description = "관리자가 회원의 권한을, ADMIN으로 변경할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 권한 변경 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (관리자 아님)"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @PutMapping("/{memberId}/role")
    public ResponseEntity<String> updateMemberRole(
            @PathVariable int memberId,
            @RequestParam RoleName roleName
    ) {
        adminService.updateMemberRole(memberId, roleName);
        return ResponseEntity.ok("회원id " + memberId + "의 권한을 " + roleName + "(으)로 변경 성공");
    }



}
