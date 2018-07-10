package com.game.tictactoe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.game.tictactoe.AbstractTest;
import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.form.AutoMoveForm;
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
public class MoveControllerTest extends AbstractTest {

    @Test
    public void autoCreateMove_SuccessTest() throws Exception {
        AutoMoveForm autoMoveForm = AutoMoveForm.builder()
                .moveNumber(2)
                .build();
        MvcResult mvcResult = mockMvc.perform(
                post("/move/autoCreate")
                        .content(objectMapper.writeValueAsBytes(autoMoveForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        MoveDto moveDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MoveDto.class);
        assertNotNull(moveDto);
        assertEquals(autoMoveForm.getMoveNumber(), moveDto.getMoveNumber());
        assertEquals(gameDto, moveDto.getGame());
    }

    @Test
    public void getMovesInGame_SuccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/move/list")
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        List<MoveDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MoveDto>>() {
        });
        assertFalse(list.isEmpty());
        for (MoveDto moveDto : list) {
            assertNotNull(moveDto);
        }
    }
}
