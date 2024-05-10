package org.zerock.securityprj2practiceback2.service;


import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.securityprj2practiceback2.domain.Member;
import org.zerock.securityprj2practiceback2.domain.MemberRole;
import org.zerock.securityprj2practiceback2.dto.MemberDTO;
import org.zerock.securityprj2practiceback2.dto.MemberModifyDTO;
import org.zerock.securityprj2practiceback2.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${kakao.get.user.url}")
    private String kakaoGetUser;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {

        // 1. accessToken 을 이용해서 사용자 이메일 정보 가져오기
        String email = getEmailFromKakaoAccessToken(accessToken);

        // 기존 DB에 회원 정보가 있는 경우
        Optional<Member> result = memberRepository.findById(email);
        if (result.isPresent()){

            MemberDTO memberDTO = entityToDTO(result.get());

            log.info("existed...................." + memberDTO);

            return memberDTO;
        }

        //---------------------------------------------------------------------

        // 없는 경우 -> 회원 만들기
        Member socialMember = makeSocialMember(email);
        memberRepository.save(socialMember);

        MemberDTO memberDTO = entityToDTO(socialMember);

        return memberDTO;
    }

    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {

        Optional<Member> result = memberRepository.findById(memberModifyDTO.getEmail());

        Member member = result.orElseThrow();

        member.changeNickname(memberModifyDTO.getNickname());
        member.changeSocial(false);
        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));

        memberRepository.save(member);
    }

    // 기존 DB 에 동일한 회원 정보가 없는 경우 -> 카카오 회원 생성 시켜주기
    private Member makeSocialMember(String email) {

        String tempPassword = makeTempPassword();

        log.info("tempPassword: " + tempPassword);

        Member member = Member.builder()
                .email(email)
                .pw(passwordEncoder.encode(tempPassword))
                .nickname("Social Member")
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }


    // 카카오에서 정보 가져오기
    private String getEmailFromKakaoAccessToken(String accessToken) {

        String kakaoGetUserURL = kakaoGetUser;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info("response --------------------------------");
        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: --- " + kakaoAccount);

        String email = kakaoAccount.get("email");

        log.info("email: " + email);

        return email;
    }

    private String makeTempPassword() {

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            buffer.append((char)((int)(Math.random() * 55) + 65));
        }
        return buffer.toString();
    }


}




















