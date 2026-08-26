package com.marcus.titan.modules.user.mapper;

import com.marcus.titan.modules.user.dto.response.UserResponse;
import com.marcus.titan.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserResponse userToResponse(User user);
    List<UserResponse> usersToResponses(List<User> users);
}
