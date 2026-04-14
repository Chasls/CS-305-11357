package com.snhu.sslserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		properties = {
				"server.ssl.enabled=false"
		})
class SslServerApplicationTests {

	@Autowired
	private org.springframework.context.ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void hashEndpointReturnsChecksumVerificationPage() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new HashController(new ChecksumService())).build();

		mockMvc.perform(get("/hash")
				.param("name", "Chas Stevens")
				.param("data", "Unique-CS305-String"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Checksum Verification")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Chas Stevens")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Unique-CS305-String")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("SHA-256")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("PASS")));
	}
}
