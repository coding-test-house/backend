package dev.codehouse.backend.user.service.external;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SolvedAcClient {
    public boolean verifyUserClass(String handle, String classes) {
        try{
            String urlStr = "https://solved.ac/profile/" + handle;
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "JavaClient");

            int responseCode = conn.getResponseCode();
            if(responseCode == 404) {
                return false;
            }
            if(responseCode != 200) {
                throw new RuntimeException("Solved.ac 서버 오류" + responseCode);
            }
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String content = in.lines().reduce("", String::concat);
                conn.disconnect();
                return content.contains(classes);
            }
        } catch (Exception e) {
            throw new RuntimeException("Solved.ac 서버 호출 실패: " + e.getMessage());
        }
    }
}
