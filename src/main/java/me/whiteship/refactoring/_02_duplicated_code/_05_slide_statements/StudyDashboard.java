package me.whiteship.refactoring._02_duplicated_code._05_slide_statements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class StudyDashboard {
	// 관련있는 코드끼리 묶여가 더 쉽게 이해할 수 있다(코드의 지역성 clean code 참조)
	// 관련있는 코드끼리 묶은다음 함수추출하기로 더 깔끔하게 분리가 가능하다.
	private void printParticipants(int eventId) throws IOException {
		// Get github issue to check homework
		GitHub gitHub = GitHub.connect();
		GHRepository repository = gitHub.getRepository("whiteship/live-study");
		GHIssue issue = repository.getIssue(eventId);

		// Get participants
		Set<String> participants = new HashSet<>();
		issue.getComments().forEach(c -> participants.add(c.getUserName()));

		// Print participants
		participants.forEach(System.out::println);
	}

	private void printReviewers() throws IOException {
		// Get github issue to check homework
		// 아래의 한줄이 원래 여기있었는데 지역성으로 인해 변수가 사용되지 직전으로 옮겨감
		// Set<String> reviewers = new HashSet<>();
		GitHub gitHub = GitHub.connect();
		GHRepository repository = gitHub.getRepository("whiteship/live-study");
		GHIssue issue = repository.getIssue(30);

		// Get reviewers
		Set<String> reviewers = new HashSet<>();
		issue.getComments().forEach(c -> reviewers.add(c.getUserName()));

		// Print reviewers
		reviewers.forEach(System.out::println);
	}

	public static void main(String[] args) throws IOException {
		StudyDashboard studyDashboard = new StudyDashboard();
		studyDashboard.printReviewers();
		studyDashboard.printParticipants(15);
	}

}
