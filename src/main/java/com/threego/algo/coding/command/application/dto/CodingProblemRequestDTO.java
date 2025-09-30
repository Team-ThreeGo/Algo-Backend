package com.threego.algo.coding.command.application.dto;

import com.threego.algo.coding.command.domain.aggregate.enums.Platform;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodingProblemRequestDTO {
    private int memberId;
    private String problemTitle;
    private String problemUrl;
    private Platform platform;   // BOJ, PGS, ETC
    private String difficulty; // 난이도
    private String content;
    private String input;
    private String output;
    private String constraints;
}
