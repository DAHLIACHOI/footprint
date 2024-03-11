package com.study.footprint.service.member;

import com.study.footprint.common.exception.CommonBadRequestException;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.auth.JwtTokenProvider;
import com.study.footprint.config.auth.TokenDto;
import com.study.footprint.domain.member.RefreshToken;
import com.study.footprint.domain.member.RefreshTokenRepository;
import com.study.footprint.dto.member.request.LoginReqDto;
import com.study.footprint.dto.member.request.TokenReqDto;
import com.study.footprint.dto.member.response.LoginResDto;
import com.study.footprint.dto.member.response.TokenResDto;
import com.study.footprint.service.common.ResponseService;
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
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthenticationManagerBuilder authenticationManagerBuilder, ResponseService responseService, JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.responseService = responseService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Transactional
    public SingleResult<LoginResDto> login(LoginReqDto loginReqDto) {

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        LoginResDto loginResDto = LoginResDto.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        return responseService.getSingleResult(loginResDto);
    }


    @Transactional
    public SingleResult<TokenResDto> reissue(TokenReqDto tokenReqDto) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
            throw new CommonBadRequestException("invalidRefreshToken");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CommonBadRequestException("logoutUser"));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())) {
            throw new CommonBadRequestException("invalidRefreshToken");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        refreshToken.updateValue(tokenDto.getRefreshToken());

        TokenResDto tokenResDto = TokenResDto.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        return responseService.getSingleResult(tokenResDto);
    }
}
