package com.rakuraku.login.service;

import com.rakuraku.login.dto.GetUsersInfoDto;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {
    private final UsersRepository usersRepository;

    // 이미 존재하는 회원인지 확인
    public boolean isUuidExist(String uuid){
        try{
            getUsers(uuid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public GetUsersInfoDto getUsers(String uuid) throws Exception{
        Users users = usersRepository.findByUserId(uuid)
                .orElseThrow(() -> new Exception("Account now found."));
        return new GetUsersInfoDto(users);
    }
}
