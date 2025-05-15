package com.kesi.planit.token;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//한번 만 실행하는 필터
//단방향 필터인 줄 알았는데 그건 아닌듯
@Slf4j
public class FirebaseTokenFilter extends OncePerRequestFilter {
    private final List<String> excludePatterns;
    private final AntPathMatcher matcher;

    public FirebaseTokenFilter(List<String> excludePatterns, AntPathMatcher matcher) {
        this.excludePatterns = excludePatterns;
        this.matcher = matcher;
    }

    //여기서 true를 리턴하면 doFilterInternal 메서드를 실행시키지 않음
    //exclude 되는 url는 필터를 실행시키지 않고 요청을 허락해 준다.(ex. join, login ...)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //stream 배열에 요소에 대해서 반복적인 작업을 람다식 형태로 표현해 수행할 수 있음
        //코드가 간결해진다.
        return excludePatterns.stream().anyMatch(pattern -> matcher.match(pattern, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        log.info("request url : {}", request.getRequestURI());

        if (header != null && header.startsWith("Bearer ")) { //header 가 있는지 판단
            String token = header.substring(7); //앞에 Barer 삭제

            log.info("token : {}", token);
            try{
                FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(token);// 들어온 토큰을 검증

                //아마 토큰이 유효하지 않으면 자동으로 Exception를 발생시킬것 같긴한데 한 번 확인 필요.
                if(decodeToken != null) {
                    request.setAttribute("uid", decodeToken.getUid());
                    request.setAttribute("email", decodeToken.getEmail());

                    filterChain.doFilter(request, response);
                }
            } catch (FirebaseAuthException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\":\"Invalid Firebase Token\"}");
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\":\"Authorization header is invalid\"}");
        }
    }
}
