package com.study.footprint.service.member;

import com.study.footprint.common.exception.CommonBadRequestException;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.auth.JwtTokenProvider;
import com.study.footprint.dto.member.request.LoginReqDto;
import com.study.footprint.dto.member.request.TokenReqDto;
import com.study.footprint.dto.member.response.LoginResDto;
import com.study.footprint.dto.member.response.TokenResDto;
import com.study.footprint.service.common.ResponseService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(AuthenticationManagerBuilder authenticationManagerBuilder, ResponseService responseService,
                       JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.responseService = responseService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }


    @Transactional
    public SingleResult<LoginResDto> login(LoginReqDto loginReqDto) {

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성

        LoginResDto loginResDto = LoginResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(authentication))
                .refreshToken(jwtTokenProvider.createRefreshToken(authentication))
                .build();

        return responseService.getSingleResult(loginResDto);
    }


    @Transactional
    public SingleResult<TokenResDto> reissue(TokenReqDto tokenReqDto) {

        // Refresh Token 검증
        jwtTokenProvider.validateToken(tokenReqDto.getRefreshToken());

        // Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        // 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

        // Refresh Token 일치하는지 검사
        if (!refreshToken.equals(tokenReqDto.getRefreshToken())) {
            throw new CommonBadRequestException("invalidRefreshToken");
        }

        // 새로운 토큰 생성
        TokenResDto tokenResDto = TokenResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(authentication))
                .refreshToken(jwtTokenProvider.createRefreshToken(authentication))
                .build();

        return responseService.getSingleResult(tokenResDto);
    }
}
