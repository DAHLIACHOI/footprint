package com.study.footprint.service.users;

import com.study.footprint.domain.user.UserRepository;
import com.study.footprint.dto.user.JoinReqDto;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Builder
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void join(JoinReqDto joinReqDto) {


    }
}
