package com.scott.neptune.searchserver.dto;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/21 23:01
 * @Description: NeptuneBlog
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "searchResultMap", description = "搜索结果")
public class SearchResultMap implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户列表
     */
    @ApiModelProperty(name = "userList", value = "用户列表")
    private List<UserDto> userList;

    /**
     * 推文列表
     */
    @ApiModelProperty(name = "tweetList", value = "推文列表")
    private List<TweetDto> tweetList;

}
