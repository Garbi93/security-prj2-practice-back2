package org.zerock.securityprj2practiceback2.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.securityprj2practiceback2.dto.MemberDTO;
import org.zerock.securityprj2practiceback2.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    // 해당 경로 주소는 필터를 거치지 않아 주는 기능
        String path = request.getRequestURI();

        log.info("check uri --------------" + path);

        // true 면 체크 안함
        if (path.startsWith("/api/member/")) {
            return true;
        }

        //이미지 조회 경로는 체크하지 않는다면
        if (path.startsWith("/api/products/view/")) {
            return true;
        }


        // false 면 체크
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("--------------- doFilterInternal ---------------");

        log.info("---------------  ---------------");

        log.info("---------------  ---------------");

        String authHeaderStr = request.getHeader("Authorization");

        try {
            //Bearer // 공백 까지 총 7 글자 이후 JWT 문자열이 나오기 때문에 잘라 내야함
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            String email = (String) claims.get("email");
            String pw = (String) claims.get("pw");
            String nickname = (String) claims.get("nickname");
            Boolean social = (Boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(email, pw, nickname, social.booleanValue(), roleNames);

            log.info("-----------------------------------");
            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // dest 다음 목적지로 이동 시키는
            filterChain.doFilter(request, response);

        }catch(Exception e){

            log.error("JWT Check Error..............");

            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }

    }
}
