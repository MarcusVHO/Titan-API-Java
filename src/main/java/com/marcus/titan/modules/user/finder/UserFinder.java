package com.marcus.titan.modules.user.finder;

import com.marcus.titan.exceptions.UserNotFoundException;
import com.marcus.titan.modules.user.dto.response.UserResponse;
import com.marcus.titan.modules.user.mapper.UserMapper;
import com.marcus.titan.modules.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserFinder {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserFinder(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse findById(Integer id) {
        return userMapper.userToResponse(userRepository.findById(Long.valueOf(id)).orElseThrow(UserNotFoundException::new));
    }

    public List<UserResponse> findUsersByIds(Collection<Integer> ids) {
        return userMapper.usersToResponses(userRepository.findAllByIdIn(ids));
    }

    public Map<Integer, String> findUserNamesByIds(
            Collection<Integer> ids
    ) {
        return findUsersByIds(ids)
                .stream()
                .collect(Collectors.toMap(
                        UserResponse::id,
                        UserResponse::name
                ));
    }
}
