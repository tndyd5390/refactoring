package me.whiteship.refactoring._01_smell_mysterious_name._02_rename_variable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class StudyDashboard {

	private Set<String> usernames = new HashSet<>();

	private Set<String> reviews = new HashSet<>();

	/**
	 * 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어옵니다.
	 * @throws IOException
	 */
	private void loadReviews() throws IOException {
		GitHub gitHub = GitHub.connect();
		GHRepository repository = gitHub.getRepository("whiteship/live-study");
		GHIssue issue = repository.getIssue(30);

		//코멘트를 가져오긴 하지만 사실상 리뷰를 읽어오는 것이기 때문에 review 가 더 어울리는듯
		List<GHIssueComment> reviews = issue.getComments();
		for (GHIssueComment review : reviews) {
			usernames.add(review.getUserName());
			this.reviews.add(review.getBody());
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

		//아래의 람다식에서 사용하는 변수의 이름은 name이지만...
		//람다식은 범위가 매우 좁고 어떤 타입을 사용하는지 대부분은 알고 있다.
		//그런 경우에는 간추려서 사용하기도 한다. name -> n
		//그러나 한글자 보다는 조금더 명시적인 것이 좋다.
		//또는 method reference로 변경이 가능하다. System.out::println
		studyDashboard.getUsernames().forEach(System.out::println);

		studyDashboard.getReviews().forEach(System.out::println);
	}
}
