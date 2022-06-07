package me.whiteship.refactoring._01_smell_mysterious_name._01_before;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudyDashboard {

    private Set<String> usernames = new HashSet<>();

    private Set<String> reviews = new HashSet<>();

    //이 함수의 선언부가 적절한가?
    // private void studyReviews(GHIssue issue) throws IOException {
    //     List<GHIssueComment> comments = issue.getComments();
    //     for (GHIssueComment comment : comments) {
    //         usernames.add(comment.getUserName());
    //         reviews.add(comment.getBody());
    //     }
    // }

    /**
     * 1. 함수의 이름 : 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어온다 -> 함수의 이름이 되고 loading라는 이름이 더 적절해보임
     * 2. 매개변수 : 이 매서드는 !단 하나!의 github 이슈를 가져오기 때문에 매개변수가 필요해보이지 않는다.
     *   매개변수 단축cmd + f6
     * @throws IOException
     */
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30); //30번 이슈에만 reviews가 달려있기 때문에 스태틱한 값이다. 따라서 이 함수는 매개변수가 필요없다.
        List<GHIssueComment> comments = issue.getComments();
        for (GHIssueComment comment : comments) {
            usernames.add(comment.getUserName());
            reviews.add(comment.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {

        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
