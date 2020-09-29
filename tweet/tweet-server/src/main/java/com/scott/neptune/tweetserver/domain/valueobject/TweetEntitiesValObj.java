package com.scott.neptune.tweetserver.domain.valueobject;

import com.scott.neptune.common.config.JpaConverterJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Annotation> annotations;

    @Convert(converter = JpaConverterJson.class)
    private List<Cashtag> cashtags;

    @Convert(converter = JpaConverterJson.class)
    private List<Hashtag> hashtags;

    @Convert(converter = JpaConverterJson.class)
    private List<Url> urls;

    @Convert(converter = JpaConverterJson.class)
    private List<Mention> mentions;

    @Data
    public static class Annotation implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer start;
        private Integer end;
        private Double probability;
        private String type;
        private String normalizedText;
    }

    @Data
    public static class Cashtag implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer start;
        private Integer end;
        private String tag;
    }

    @Data
    public static class Hashtag implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer start;
        private Integer end;
        private String tag;
    }

    @Data
    public static class Mention implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer start;
        private Integer end;
        private String tag;
    }

    @Data
    public static class Url implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer start;
        private Integer end;
        private String url;
        private String expandedUrl;
        private String displayUrl;
        private String status;
        private String title;
        private String description;
        private String unwoundUrl;
    }

}