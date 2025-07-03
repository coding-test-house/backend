package dev.codehouse.backend.problem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codehouse.backend.global.exception.ExternalApiException;
import dev.codehouse.backend.global.exception.ProblemException;
import dev.codehouse.backend.global.exception.UserException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.problem.domain.Problem;
import dev.codehouse.backend.problem.repository.ProblemRepository;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static dev.codehouse.backend.global.response.ResponseCode.PROBLEM_NOT_FOUND;
import static dev.codehouse.backend.global.response.ResponseCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProblemCheckService {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final String BASE_URL = "https://solved.ac/api/v3";

    public boolean checkSolvedProblem(String userId, int problemId) {
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
                throw new ExternalApiException(ResponseCode.EXTERNAL_API_ERROR);
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
            throw new ExternalApiException(ResponseCode.EXTERNAL_API_ERROR);
        }
    }
}
