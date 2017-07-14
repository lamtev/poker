package com.lamtev.poker.webserver.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.lamtev.poker.webserver.controllers.Util.validRoomJson;
import static org.hamcrest.Matchers.*;
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
        createRoom();

        start();

        mockMvc.perform(get("/rooms/xxx/players"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].*.wager", hasItems(0, 25, 50)))
                .andExpect(jsonPath("$.[*].*.stack", hasItems(25000, 24975, 24950)))
                .andExpect(jsonPath("$.[*].*.active", hasItem(true)));
    }

    @Test
    public void testGetPlayersWhenNotStarted() throws Exception {
        createRoom();

        mockMvc.perform(get("/rooms/xxx/players"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is(409)))
                .andExpect(jsonPath("$.message",
                        is("There isn't players info because game has not been started. Room is free")));
    }

    @Test
    public void testGetPlayerCards() throws Exception {
        createRoom();

        start();

        mockMvc.perform(get("/rooms/xxx/players/Anton/cards"))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testGetCommunityCardsWhenNoCards() throws Exception {
        createRoom();

        start();

        mockMvc.perform(get("/rooms/xxx/communityCards"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("No cards")));
    }

    @Test
    public void testGetCommunityCards() throws Exception {
        createRoom();

        start();

        callNTimes(5);

        mockMvc.perform(post("/rooms/xxx/check"))
                .andDo(print());

        mockMvc.perform(get("/rooms/xxx/communityCards"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void testGetShortInfo() throws Exception {
        createRoom();

        start();

        mockMvc.perform(get("/rooms/xxx/short-info"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state", is("PreflopWageringPokerState")))
                .andExpect(jsonPath("$.bank", is(75)))
                .andExpect(jsonPath("$.wager", is(50)));
    }

    @Test
    public void testGetShortInfoWhenNotStarted() throws Exception {
        createRoom();

        mockMvc.perform(get("/rooms/xxx/short-info"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is(409)))
                .andExpect(jsonPath("$.message",
                        is("There isn't info because game has not been started. Room is free")));
    }

    @Test
    public void testCall() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/call"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testCallWhenUnallowable() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/call"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testCheck() throws Exception {
        createRoom();

        start();

        callNTimes(5);

        mockMvc.perform(post("/rooms/xxx/check"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }


    @Test
    public void testCheckWhenUnallowable1() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/check"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("Check is not allowable")));
    }

    @Test
    public void testCheckWhenUnallowable2() throws Exception {
        createRoom();

        start();

        callNTimes(5);

        mockMvc.perform(post("/rooms/xxx/raise")
                .param("additionalWager", "100"))
                .andDo(print());

        mockMvc.perform(post("/rooms/xxx/check"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("Check is not allowable")));
    }

    @Test
    public void testRaise() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/raise")
                .param("additionalWager", "100"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testRaiseNegativeWager() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/raise")
                .param("additionalWager", "-100"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("Wager can not be negative")));
    }

    @Test
    public void testRaiseNotEnoughMoney() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/raise")
                .param("additionalWager", "100000"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message",
                        is("There is not enough money to make a move. Try to make allIn")));
    }

    @Test
    public void testAllIn() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/allIn"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testFold() throws Exception {
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/fold"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testShowDown() throws Exception {
        //TODO
        createRoom();

        start();

        mockMvc.perform(post("/rooms/xxx/showDown"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("Show down is forbidden move in PreflopWageringPokerState")));
    }

    private void createRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(validRoomJson()))
                .andDo(print());
    }

    private void start() throws Exception {
        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"))
                .andDo(print());
    }

    private void callNTimes(int n) throws Exception {
        for (int i = 0; i < n; ++i) {
            mockMvc.perform(post("/rooms/xxx/call"))
                    .andDo(print());
        }
    }

}
