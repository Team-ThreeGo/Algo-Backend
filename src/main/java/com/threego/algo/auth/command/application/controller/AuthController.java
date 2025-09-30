package com.threego.algo.auth.command.application.controller;

import com.threego.algo.auth.command.application.dto.RequestLoginDTO;
import com.threego.algo.auth.command.application.dto.RequestRegistUserDTO;
import com.threego.algo.auth.command.application.dto.ResponseRegistUserDTO;
import com.threego.algo.auth.command.application.dto.UserDTO;
import com.threego.algo.auth.command.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(
        name = "Auth - User",
        description = "회원용 인증 API"
)
@RestController
public class AuthController {

    private AuthService authService;
    private final ModelMapper modelMapper;

    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;

    }
    @Operation(
            summary = "회원가입",
            description = "사용자가 입력한 이메일, 비밀번호, 닉네임으로 회원가입을 합니다."
    )
    @PostMapping("/signup")
    public ResponseEntity<ResponseRegistUserDTO> registUser(@RequestBody RequestRegistUserDTO newUser) {
        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);

        authService.registUser(userDTO);

        ResponseRegistUserDTO responseUser = modelMapper.map(userDTO, ResponseRegistUserDTO.class);
        log.info(responseUser.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(
               summary = "로그인",
               description = "이메일과 비밀번호를 이용하여 로그인"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "\"로그인 성공: user@example.com\""))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (이메일 또는 비밀번호 불일치)",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestLoginDTO requestLoginDTO) {
        String email = requestLoginDTO.getEmail();
        String password = requestLoginDTO.getPassword();

        // 실제 로그인 로직 대신 예시 응답
        return ResponseEntity.ok("로그인 성공: " + email);
    }
}
