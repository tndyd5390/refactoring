package me.whiteship.refactoring._02_duplicated_code._04_extract_function;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class StudyDashboard {
	//"의도"와 "구현" 분리하기
	// 코드를 볼때 이해가 되지 않는다면 구현이고 코드를 볼때 책 읽는 것처럼 이해가 된다면 의도라고 볼수있다 -> 물론 이것도 주관적인 기준임
	// 무슨 일을 하는 코드인지 알아내려고 노력해야 하는 코드라면 해당 코드를 함수로 분리하고 함수 이름으로 "무슨일을 하는지" 표현할 수 있다.
	//한줄 짜리 메소드도 괜찮은가? -> 나는 괜찮다고 생각한다. 기선 아저씨도 의도를 드러낼수 있다면 괜찮다고 생각함
	// 거대한 함수 안에 들어있는 주석은 추출한 함수를 찾는데 있어서 좋은 단서임
	private void printParticipants(int eventId) throws IOException {
		GHIssue issue = getGhIssue(eventId);
		Set<String> participants = getUsernames(issue);
		print(participants);
	}

	private void print(Set<String> participants) {
		participants.forEach(System.out::println);
	}

	private Set<String> getUsernames(GHIssue issue) throws IOException {
		Set<String> usernames = new HashSet<>();
		issue.getComments().forEach(c -> usernames.add(c.getUserName()));
		return usernames;
	}

	private GHIssue getGhIssue(int eventId) throws IOException {
		GitHub gitHub = GitHub.connect();
		GHRepository repository = gitHub.getRepository("whiteship/live-study");
		GHIssue issue = repository.getIssue(eventId);
		return issue;
	}

	private void printReviewers() throws IOException {
		GHIssue issue = getGhIssue(30);
		Set<String> reviewers = getUsernames(issue);
		print(reviewers);
	}

	public static void main(String[] args) throws IOException {
		StudyDashboard studyDashboard = new StudyDashboard();
		studyDashboard.printReviewers();
		studyDashboard.printParticipants(15);
	}

}
