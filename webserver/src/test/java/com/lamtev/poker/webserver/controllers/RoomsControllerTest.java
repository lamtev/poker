package com.lamtev.poker.webserver.controllers;

import com.lamtev.poker.webserver.controllers.exceptions.RequestBodyHasUnsuitableFormatException;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.lamtev.poker.webserver.controllers.Util.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RoomsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetNonexistentRooms() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("Rooms not found")));
    }

    @Test
    public void testGetNonexistentRoom() throws Exception {
        mockMvc.perform(get("/rooms/xyz"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message",
                        is(new ResourceNotFoundException("Rooms").getMessage())));

        createValidRoom();

        mockMvc.perform(get("/rooms/xyz"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message",
                        is(new ResourceNotFoundException("Room with id xyz").getMessage())));
    }

    @Test
    public void testCreateRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(validRoomJson()))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("xxx")))
                .andExpect(jsonPath("$.playersNumber", is(6)))
                .andExpect(jsonPath("$.stack", is(25000)))
                .andExpect(jsonPath("$.free", is(true)));
    }

    @Test
    public void testCreateInvalidRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidRoomJson()))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(422)))
                .andExpect(jsonPath("$.message",
                        is(new RequestBodyHasUnsuitableFormatException().getMessage())));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        createValidRoom();

        mockMvc.perform(put("/rooms/xxx")
                .contentType(APPLICATION_JSON_UTF8)
                .content(updatedValidRoomJson()))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("zzz")))
                .andExpect(jsonPath("$.playersNumber", is(7)))
                .andExpect(jsonPath("$.stack", is(50000)))
                .andExpect(jsonPath("$.free", is(true)));
    }

    @Test
    public void testUpdateRoomUnsuccessfully() throws Exception {
        createValidRoom();

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"))
                .andDo(print());

        mockMvc.perform(put("/rooms/xxx")
                .contentType(APPLICATION_JSON_UTF8)
                .content(updatedValidRoomJson()))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is(409)))
                .andExpect(jsonPath("$.message", is("Can not update room. Room is taken")));
    }

    @Test
    public void testGetExistentRoom() throws Exception {
        createValidRoom();

        mockMvc.perform(get("/rooms/xxx"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("xxx")))
                .andExpect(jsonPath("$.playersNumber", is(6)))
                .andExpect(jsonPath("$.stack", is(25000)))
                .andExpect(jsonPath("$.free", is(true)));
    }

    @Test
    public void testStartWhenUnavailable() throws Exception {
        createValidRoom();

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"))
                .andDo(print());

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "User"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is(409)))
                .andExpect(jsonPath("$.message",
                        is("Game has been already started. Room is taken")));
    }

    @Test
    public void testStartWhenAvailable() throws Exception {
        createValidRoom();

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private void createValidRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(validRoomJson()))
                .andDo(print());
    }

}