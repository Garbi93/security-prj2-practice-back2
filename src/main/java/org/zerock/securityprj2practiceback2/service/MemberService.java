package org.zerock.securityprj2practiceback2.service;


import org.springframework.transaction.annotation.Transactional;
import org.zerock.securityprj2practiceback2.dto.MemberDTO;

@Transactional
public interface MemberService {

    MemberDTO getKakaoMember(String accessToken);

}
