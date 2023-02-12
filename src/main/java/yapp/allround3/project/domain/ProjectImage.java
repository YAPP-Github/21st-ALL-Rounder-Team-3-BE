package yapp.allround3.project.domain;

import lombok.Getter;

import java.util.List;
import java.util.Random;

@Getter
public enum ProjectImage {
    PROJECT_IMAGE_RED("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%88%E1%85%A1%E1%86%AF%E1%84%80%E1%85%A1%E1%86%BC.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%88%E1%85%A1%E1%86%AF%E1%84%80%E1%85%A1%E1%86%BC.png"),
    PROJECT_IMAGE_PURPLE("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%87%E1%85%A9%E1%84%85%E1%85%A1.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%87%E1%85%A9%E1%84%85%E1%85%A1.png"),
    PROJECT_IMAGE_GREEN("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%8E%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%8E%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8.png"),
    PROJECT_IMAGE_PINK("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%87%E1%85%AE%E1%86%AB%E1%84%92%E1%85%A9%E1%86%BC.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%87%E1%85%AE%E1%86%AB%E1%84%92%E1%85%A9%E1%86%BC.png"),
    PROJECT_IMAGE_ORANGE("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%8C%E1%85%AE%E1%84%92%E1%85%AA%E1%86%BC.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%8C%E1%85%AE%E1%84%92%E1%85%AA%E1%86%BC.png"),
    PROJECT_IMAGE_BLUE("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%91%E1%85%A1%E1%84%85%E1%85%A1%E1%86%BC.png",
            "https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/team_thumbnail/%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF_%E1%84%91%E1%85%A1%E1%84%85%E1%85%A1%E1%86%BC.png"),

    //GRAY는 프로젝트 종료 시에만 사용
    PROJECT_IMAGE_GRAY("https://timitimi-image-bucket.s3.ap-northeast-2.amazonaws.com/project_thumbnail/%E1%84%87%E1%85%A2%E1%84%80%E1%85%A7%E1%86%BC_%E1%84%92%E1%85%AC%E1%84%89%E1%85%A2%E1%86%A8.png", "");

    private final String projectThumbnailUrl;
    private final String teamThumbnailUrl;

    ProjectImage(String projectThumbnailUrl, String teamThumnbnailUrl) {
        this.projectThumbnailUrl = projectThumbnailUrl;
        this.teamThumbnailUrl = teamThumnbnailUrl;
    }

    private static final List<ProjectImage> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size() - 1;
    private static final Random RANDOM = new Random();


    public static ProjectImage randomProjectThumbnail() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
