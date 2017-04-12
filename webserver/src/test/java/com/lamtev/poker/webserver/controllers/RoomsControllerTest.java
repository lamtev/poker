package com.lamtev.poker.webserver.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.hamcrest.Matchers.is;
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
public class RoomsControllerTest {

    private static String ROOM_JSON;

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
                .andExpect(jsonPath("$.message", is("Rooms not found")));

        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()));

        mockMvc.perform(get("/rooms/xyz"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("Room with id xyz not found")));
    }

    @Test
    public void testCreateRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("xxx")))
                .andExpect(jsonPath("$.playersNumber", is(6)))
                .andExpect(jsonPath("$.stack", is(25000)))
                .andExpect(jsonPath("$.free", is(true)));
    }

    @Test
    public void testGetExistentRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()));

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
    public void testStart() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()));

        mockMvc.perform(post("/rooms/xxx/start")
                .param("name", "Anton"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is("xxx")))
                .andExpect(jsonPath("$.playersNumber", is(6)))
                .andExpect(jsonPath("$.stack", is(25000)))
                .andExpect(jsonPath("$.free", is(false)));
    }

    @Test
    public void testGetPlayersWhenStarted() throws Exception {
        mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON_UTF8)
                .content(roomJson()));

        mockMvc.perform(get("/rooms/xxx/players"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    private static String roomJson() {
        if (ROOM_JSON == null) {
            try {
                ROOM_JSON = new Scanner(new File("src/test/resources/room.json")).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ROOM_JSON;
    }

}