package com.game.tictactoe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.game.tictactoe.AbstractTest;
import com.game.tictactoe.dto.GameDto;
import com.game.tictactoe.entity.enums.GameStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameControllerTest extends AbstractTest {

    @Test
    public void getGameById_SuccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/game/{id}", AbstractTest.gameDto.getId())
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        GameDto gameDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GameDto.class);
        assertNotNull(gameDto);
        assertNotNull(gameDto.getName());
        assertEquals(GameControllerTest.gameDto.getName(), gameDto.getName());
        assertNotNull(gameDto.getType());
        assertEquals(GameControllerTest.gameDto.getType(), gameDto.getType());
        assertNotNull(gameDto.getStatus());
        assertEquals(GameStatus.IN_PROGRESS, gameDto.getStatus());
    }

    @Test
    public void joinGame_SuccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post("/game/join")
                        .content(objectMapper.writeValueAsBytes(GameControllerTest.gameDto.toGameForm()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        GameDto gameDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GameDto.class);
        assertNotNull(gameDto);
        assertNotNull(gameDto.getName());
        assertEquals(GameControllerTest.gameDto.getName(), gameDto.getName());
        assertNotNull(gameDto.getType());
        assertEquals(GameControllerTest.gameDto.getType(), gameDto.getType());
        assertNotNull(gameDto.getStatus());
        assertEquals(GameStatus.IN_PROGRESS, gameDto.getStatus());
    }

    @Test
    public void getAllGames_SuccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/game/list")
        )
                .andExpect(status().isOk())
                .andReturn();
        List<GameDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<GameDto>>() {
        });
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        GameDto gameDto = list.get(0);
        assertNotNull(gameDto.getName());
        assertEquals(GameControllerTest.gameDto.getName(), gameDto.getName());
        assertNotNull(gameDto.getType());
        assertEquals(GameControllerTest.gameDto.getType(), gameDto.getType());
        assertNotNull(gameDto.getStatus());
        assertEquals(GameStatus.IN_PROGRESS, gameDto.getStatus());
    }
}
