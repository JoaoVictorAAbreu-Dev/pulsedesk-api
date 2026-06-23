package com.jv.incidentflow.ticket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @Test
  void shouldRequireAuthenticationForTicketsEndpoint() throws Exception {
    mockMvc.perform(get("/api/tickets")).andExpect(status().isUnauthorized());
  }

  @Test
  void shouldExposeTicketsEndpointWithBearerToken() throws Exception {
    var token = loginAndGetToken();

    mockMvc.perform(get("/api/tickets").header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk());
  }

  private String loginAndGetToken() throws Exception {
    var response = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"email":"demo@pulsedesk.dev","password":"pulse123"}
                """))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    JsonNode node = objectMapper.readTree(response);
    return node.get("token").asText();
  }
}
