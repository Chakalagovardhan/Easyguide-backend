package com.gova.EasyGuide;

import com.gova.EasyGuide.service.db1.Users.GeminiService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasyGuideApplicationTests {

	@Autowired
	private GeminiService geminiService;

	@Test
	void contextLoads() {
	}

	@Test
    void geminreposne(ChatClient.Builder builder)
	{
		String response = geminiService.getChatReposne(builder);
		System.out.println(response);
	}

}
