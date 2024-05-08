package org.zerock.securityprj2practiceback2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.securityprj2practiceback2.util.CustomJWTException;
import org.zerock.securityprj2practiceback2.util.JWTUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       String refreshToken
    ) {

        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID STRING");
        }

        // Bearer 잘라 오기
        String accessToken = authHeader.substring(7);

        // access Token 만료 여부 확인
        //Access 토큰이 만료 되지 않았 다면
        if (checkExpiredToken(accessToken) == false) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }// 만료된게 아니면 기존 것들 갖고 다시 나가기

        //Access 토큰이 만료 되었 다면
        //Refresh 토큰 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);

        log.info("refresh ... claims: " + claims);

        String newAccessToken = JWTUtil.generateToken(claims, 10); // 새로운 accessToken 발급

        // refresh Token 값 시간 검증 후 재발급? 아님 남길지
        String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        // 새로운 refresh , access 를 들고 나가기
        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);


    }

    //시간이 1시간 미만 으로 남았 다면
    private boolean checkTime(Integer exp) {

        //JWT exp 를 날짜로 변환
        java.util.Date expDate = new java.util.Date((long) exp * (1000));

        //현재 시간과 차이 계산 - ms
        long gap = expDate.getTime() - System.currentTimeMillis();

        //분단위 계산
        long leftMin = gap / (1000 * 60);

        //1시간도 안 남았 는지..
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try{
            JWTUtil.validateToken(token);
        }catch(CustomJWTException ex) {
            if(ex.getMessage().equals("Expired")){
                return true;
            }
        }
        return false;
    }

}
