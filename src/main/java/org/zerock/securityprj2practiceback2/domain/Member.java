package org.zerock.securityprj2practiceback2.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")
public class Member {

    @Id
    private String email;

    private String pw;

    private String nickname;

    protected boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    // 사용자 권한 추가
    public void addRole(MemberRole memberRole){
        memberRoleList.add(memberRole);
    }

    // 사용자 권한 클리어 하기
    public void clearRole(){
        memberRoleList.clear();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePw(String pw){
        this.pw = pw;
    }

    public void changeSocial(boolean social) {
        this.social = social;
    }

}
