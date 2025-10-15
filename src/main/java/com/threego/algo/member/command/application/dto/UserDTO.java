package com.threego.algo.member.command.application.dto;

import com.threego.algo.member.command.domain.aggregate.MemberRank;
import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private MemberRank memberRank;
}


