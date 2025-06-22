package dev.codehouse.backend.solvedscore.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.HTTP;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolvedClient {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    public static final String BASE_URL = "https://solved.ac/api/v3";

    /**
     * UserId 를 가진 사용자가 ProblemId 문제를 풀었는지 반환하는 함수
     * @param userId 사용자
     * @param problemId 문제 번호
     * @return 풀었으면 true, 아니면 false
     */
    public boolean checkSolved(String userId, int problemId) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(BASE_URL + "/search/problem")
                    .queryParam("query", "solved_by:" + userId + " " + problemId)
                    .build()
                    .toUri();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.warn("Solved.ac 응답 실패: status={}", response.statusCode());
                return false;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode items = root.get("items");

            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    int id = item.get("problemId").asInt();
                    if (id == problemId) {
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("External API Error: Failed to check problem status", e);
        }
    }

    /**
     * UserId 를 가진 사용자가 풀었던 문제를 List 형태로 반환
     * @param userId 사용자
     * @param size 문제 개수 (기본 20, 최대 50)
     * @return 문제 번호 List
     */
    public List<Integer> getProblemIds(String userId, int size) {
        List<Integer> problemIds = new ArrayList<>();
        int page = 1;

        try {
            while (problemIds.size() < size) {
                URI uri = UriComponentsBuilder
                        .fromUriString(BASE_URL + "/search/problem")
                        .queryParam("query", "solved_by:" + userId)
                        .queryParam("sort", "level")
                        .queryParam("direction", "desc")
                        .queryParam("page", page++)
                        .build()
                        .toUri();

                HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) break;

                JsonNode items = objectMapper.readTree(response.body()).get("items");
                if (items == null || items.isEmpty()) break;

                for (JsonNode item : items) {
                    problemIds.add(item.get("problemId").asInt());
                    if (problemIds.size() >= size) break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("푼 문제 번호 조회 실패", e);
        }

        return problemIds;
    }
}
