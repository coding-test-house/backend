
package dev.codehouse.backend.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")  // MongoDB 컬렉션 이름 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;  // MongoDB의 ObjectId는 String으로 처리

    @Indexed(unique = true)  // 유니크 인덱스 생성
    private String username;

    private String password;

    private String classes;

    // 추후 역할 확장 고려
    @Builder.Default
    private String role = "USER";

    // 예: 누적 포인트
    @Builder.Default
    private int startPoint = 0;

    @Builder.Default
    private int point = 0;

    @Builder.Default
    private List<List<String>> gameResults = new ArrayList<>();

    @Builder.Default
    private List<String> solvedProblems = new ArrayList<>();

    public static User of(String username, String encodedPassword, String classes) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .role("USER")
                .startPoint(500)
                .point(500)
                .classes(classes)
                .solvedProblems(new ArrayList<>())
                .gameResults(new ArrayList<>())
                .build();
    }

    public void adjustPoint(int delta) {
        this.point += delta;
    }

    public void addGameResult(List<String> result) {
        this.gameResults.add(result);
    }

    public boolean checkProblemSolved(String problemNo) {
        if(solvedProblems.contains(problemNo)) {
            throw new RuntimeException("This problem has already been solved");
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.acmicpc.net/status?problem_id="+problemNo+"&user_id="+username +"&language_id=-1&result_id=4"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.toString().contains("맞았습니다")) solvedProblems.add(problemNo);
            else throw new RuntimeException("This problem is not solved");
            return response.statusCode() == 200 && response.body().contains(username);
        } catch (Exception e) {
            throw new RuntimeException("External API Error: Failed to check problem status", e);
        }
    }

    public boolean hasSolvedProblem(String problemNo) {
        return solvedProblems.contains(problemNo);
    }

    public void addSolvedProblem(String problemNo) {
        if (solvedProblems.contains(problemNo)) {
            throw new RuntimeException("이미 풀었던 문제입니다");
        }
        solvedProblems.add(problemNo);
    }
}