package me.whiteship.refactoring._02_duplicated_code._06_pull_up_method;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Dashboard {
	// 메소드 올리기
	// 상속 관계에서 중복 코드가 하위 클래스에 있을 경우 상위 클래스로 올려버린다.
	// refactor -> pull members up, push members down 으로 리팩토링이 가능하다.
	// 하위 클래스의 중복 메소드를 상위 클래스로 옮기는 방법
	public static void main(String[] args) throws IOException {
		ReviewerDashboard reviewerDashboard = new ReviewerDashboard();
		reviewerDashboard.printReviewers();

		ParticipantDashboard participantDashboard = new ParticipantDashboard();
		participantDashboard.printUsernames(15);
	}

	public void printUsernames(int eventId) throws IOException {
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
}
