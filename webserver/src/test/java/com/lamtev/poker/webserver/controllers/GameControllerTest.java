package com.lamtev.poker.webserver.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.lamtev.poker.webserver.controllers.Util.roomJson;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPlayersWhenStarted() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()));

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"));

        mockMvc.perform(get("/rooms/xxx/players"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.wager", hasItems(0, 25, 50)))
                .andExpect(jsonPath("$.*.stack", hasItems(25000, 24975, 24950)))
                .andExpect(jsonPath("$.*.active", hasItem(true)));
    }

}
