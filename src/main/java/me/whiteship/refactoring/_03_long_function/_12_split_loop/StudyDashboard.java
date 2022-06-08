package me.whiteship.refactoring._03_long_function._12_split_loop;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class StudyDashboard {
    // 반복문 쪼개기
    // 성능 문제를 야기할 수 있지만 리팩토링은 성능 최적화와는 별개의 작업임, 리팩토링 이후에 성능 최적화를 시도할 수 있음

    private final int totalNumberOfEvents;
    private final List<Participant> participants;
    private final Participant[] firstParticipantsForEachEvent;

    public StudyDashboard(int totalNumberOfEvents) {
        this.totalNumberOfEvents = totalNumberOfEvents;
        //동시에 여러 스레드에서 하나의 리스트를 수정할 경우 사용하는 리스트 컨커런시 지원
        participants = new CopyOnWriteArrayList<>();
        firstParticipantsForEachEvent = new Participant[this.totalNumberOfEvents];
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StudyDashboard studyDashboard = new StudyDashboard(15);
        studyDashboard.print();
    }

    private void print() throws IOException, InterruptedException {
        checkGithubIssues(getGhRepository());
        new StudyPrinter(this.totalNumberOfEvents, this.participants).execute();
        printFirstParticipants();
    }

    private void checkGithubIssues(GHRepository ghRepository) throws InterruptedException {
        //멀티스레딩 프로그래밍 코드
        //8개의 스레드 풀
        ExecutorService service = Executors.newFixedThreadPool(8);
        //각 스레드가 끝날는것을 센다
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);

        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        GHIssue issue = ghRepository.getIssue(eventId);
                        List<GHIssueComment> comments = issue.getComments();

                        //기존의 루프를 두개로 쪼갠다.
                        checkHomework(comments, eventId);
                        firstParticipantsForEachEvent[eventId - 1] = findFirst(comments);

                        latch.countDown();
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }

        latch.await();
        service.shutdown();
    }

    private Participant findFirst(List<GHIssueComment> comments) throws IOException {
        Date firstCreatedAt = null;
        Participant first = null;
        for (GHIssueComment comment : comments) {
            Participant participant = findParticipant(comment.getUserName(), participants);
             if (firstCreatedAt == null || comment.getCreatedAt().before(firstCreatedAt)) {
                firstCreatedAt = comment.getCreatedAt();
                first = participant;
            }
        }
        return first;
    }

    private void checkHomework(List<GHIssueComment> comments, int eventId) {
        for (GHIssueComment comment : comments) {
            Participant participant = findParticipant(comment.getUserName(), participants);
            participant.setHomeworkDone(eventId);
        }
    }

    private void printFirstParticipants() {
        Arrays.stream(this.firstParticipantsForEachEvent).forEach(p -> System.out.println(p.username()));
    }

    private GHRepository getGhRepository() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        return repository;
    }

    private Participant findParticipant(String username, List<Participant> participants) {
        return isNewParticipant(username, participants) ?
                createNewParticipant(username, participants) :
                findExistingParticipant(username, participants);
    }

    private Participant findExistingParticipant(String username, List<Participant> participants) {
        Participant participant;
        participant = participants.stream().filter(p -> p.username().equals(username)).findFirst().orElseThrow();
        return participant;
    }

    private Participant createNewParticipant(String username, List<Participant> participants) {
        Participant participant;
        participant = new Participant(username);
        participants.add(participant);
        return participant;
    }

    private boolean isNewParticipant(String username, List<Participant> participants) {
        return participants.stream().noneMatch(p -> p.username().equals(username));
    }

}
