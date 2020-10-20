package com.scott.neptune.userserver.component;

import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import com.scott.neptune.userserver.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class FriendshipComponent {

    private final FriendshipRepository friendshipRepository;

    public UserDto fillUserConnection(UserDto user, Long authUserId) {
        List<UserDto> singleUserList = Collections.singletonList(user);
        this.fillUserConnections(singleUserList, authUserId);
        return singleUserList.get(0);
    }

    public Collection<UserDto> fillUserConnections(Collection<UserDto> userDtoList, Long authUserId) {

        List<Long> userIds = userDtoList.stream().map(UserDto::getId).collect(Collectors.toList());

        Collection<Long> followingIds = filtrateFollowingIds(userIds, authUserId);
        Collection<Long> followerIds = filtrateFollowerIds(userIds, authUserId);

        userDtoList.forEach(userDto -> {
            if (followingIds.contains(userDto.getId())) {
                userDto.addConnection(UserConnectionEnum.FOLLOWING);
            }
            if (followerIds.contains(userDto.getId())) {
                userDto.addConnection(UserConnectionEnum.FOLLOWED_BY);
            }
        });
        return userDtoList;
    }

    public Collection<Long> filtrateFollowingIds(Collection<Long> userIds, Long authUserId) {
        return friendshipRepository
                .findAllBySourceUserAndTargetUserIn(authUserId, userIds, Sort.by(Sort.Order.desc("followDate")))
                .stream().map(friendship -> friendship.getTargetUser().getId()).collect(Collectors.toSet());
    }

    public Collection<Long> filtrateFollowerIds(Collection<Long> userIds, Long authUserId) {
        return friendshipRepository
                .findAllByTargetUserAndSourceUserIn(authUserId, userIds, Sort.by(Sort.Order.desc("followDate")))
                .stream().map(friendship -> friendship.getSourceUser().getId()).collect(Collectors.toSet());
    }
}
