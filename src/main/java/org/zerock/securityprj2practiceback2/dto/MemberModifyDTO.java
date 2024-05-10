package org.zerock.securityprj2practiceback2.dto;

import lombok.Data;

@Data
public class MemberModifyDTO {

    private String email;

    private String pw;

    private String nickname;
}
