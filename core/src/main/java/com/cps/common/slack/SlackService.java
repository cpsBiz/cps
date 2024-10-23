package com.cps.common.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SlackService {

	private final RestTemplate restTemplate;

	@Value("${dotpitch.click.domain.url}")
	private String slackWebhookUrl;

	public SlackService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void sendSlackMessage(String message) {
		Map<String, String> payload = new HashMap<>();
		payload.put("text", message);
		restTemplate.postForEntity(slackWebhookUrl, payload, String.class);
	}
}
