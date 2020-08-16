package com.scott.neptune.tweetserver.domain.valueobject;

import com.scott.neptune.common.config.JpaConverterJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TweetEntitiesValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Convert(converter = JpaConverterJson.class)
    private List<Hashtag> hashtags;

    @Convert(converter = JpaConverterJson.class)
    private List<Url> urls;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "user_mentions")
    private List<UserMentions> userMentions;

    @Convert(converter = JpaConverterJson.class)
    private List<Symbols> symbols;
}

@Data
class Hashtag implements Serializable {
    private Integer[] indices;
    private String text;
}

@Data
class Url implements Serializable {
    private Integer[] indices;
    private String url;
    private String displayUrl;
    private String expandedUrl;
}

@Data
class UserMentions implements Serializable {
    private String name;
    private Integer[] indices;
    private String screenName;
    private Long id;
}

@Data
class Symbols implements Serializable {
    private Integer[] indices;
    private String text;
}

//TODO media