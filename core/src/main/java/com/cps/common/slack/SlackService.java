package com.cps.common.slack;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SlackService {

	private final RestTemplate restTemplate;

	private String slackWebhookUrl;

	public SlackService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void sendSlackMessage(String message) {
		Map<String, String> payload = new HashMap<>();
		payload.put("text", message);
		restTemplate.postForEntity("https://hooks.slack.com/services/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", payload, String.class);
	}
}
