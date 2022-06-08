package me.whiteship.refactoring._03_long_function._10_replace_function_with_command;

import java.io.IOException;
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
    //함수를 명령으로 바꾸기
    //함수를 command로 만들어 사용한다
    //커맨드 패턴을 사용하면 부가적으로 undo, 더 복잡한 기능, 상속이나 템플릿을 사용할 수 있다.
    private final int totalNumberOfEvents;

    public StudyDashboard(int totalNumberOfEvents) {
        this.totalNumberOfEvents = totalNumberOfEvents;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StudyDashboard studyDashboard = new StudyDashboard(15);
        studyDashboard.print();
    }

    private Participant findParticipant(String username, List<Participant> participants) {
        Participant participant = null;
        if (participants.stream().noneMatch(p -> p.username().equals(username))) {
            participant = new Participant(username);
            participants.add(participant);
        } else {
            participant = participants.stream().filter(p -> p.username().equals(username)).findFirst().orElseThrow();
        }
        return participant;
    }

    private void print() throws IOException, InterruptedException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        List<Participant> participants = new CopyOnWriteArrayList<>();

        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);

        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        GHIssue issue = repository.getIssue(eventId);
                        List<GHIssueComment> comments = issue.getComments();

                        for (GHIssueComment comment : comments) {
                            Participant participant = findParticipant(comment.getUserName(), participants);
                            participant.setHomeworkDone(eventId);
                        }

                        latch.countDown();
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }

        latch.await();
        service.shutdown();

        //이부분은 커맨드로 변경해보자
        // try (FileWriter fileWriter = new FileWriter("participants.md");
        //     PrintWriter writer = new PrintWriter(fileWriter)) {
        //     participants.sort(Comparator.comparing(Participant::username));
        //
        //     writer.print(header(participants.size()));
        //
        //     participants.forEach(p -> {
        //         String markdownForHomework = getMarkdownForParticipant(p);
        //         writer.print(markdownForHomework);
        //     });
        // }

        //StudyPrinter 라는 객체의 명령으로 분리가 가능하다.
        new StudyPrinter(this.totalNumberOfEvents, participants).execute();
    }





}
