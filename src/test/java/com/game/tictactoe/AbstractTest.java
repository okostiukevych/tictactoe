package com.game.tictactoe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.tictactoe.dto.GameDto;
import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.form.MoveForm;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static MockMvc mockMvc;
    protected static MockHttpSession mockSession;

    protected static GameDto gameDto;
    protected static MoveDto moveDto;

    @Before
    public void setUp() throws Exception {
        if (isNull(mockMvc)) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
        if (isNull(mockSession)) {
            mockSession = new MockHttpSession(webApplicationContext.getServletContext(), UUID.randomUUID().toString());
        }
        if (isNull(gameDto)) {
            gameDto = createNewGame();
        }
        if (isNull(moveDto)) {
            moveDto = createNewMove();
        }
    }

    private GameDto createNewGame() throws Exception {
        GameForm gameForm = GameForm.builder()
                .name("Game_1")
                .type(GameType.ONE_VS_ONE)
                .build();
        MvcResult mvcResult = mockMvc.perform(
                post("/game/create")
                        .content(objectMapper.writeValueAsBytes(gameForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        GameDto gameDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GameDto.class);
        assertNotNull(gameDto);
        assertNotNull(gameDto.getName());
        assertEquals(gameForm.getName(), gameDto.getName());
        assertNotNull(gameDto.getType());
        assertEquals(gameForm.getType(), gameDto.getType());
        assertNotNull(gameDto.getStatus());
        assertEquals(GameStatus.IN_PROGRESS, gameDto.getStatus());
        return gameDto;
    }

    private MoveDto createNewMove() throws Exception {
        MoveForm moveForm = MoveForm.builder()
                .boardRowNumber(1)
                .boardColumnNumber(1)
                .moveNumber(1)
                .build();
        MvcResult mvcResult = mockMvc.perform(
                post("/move/create")
                        .content(objectMapper.writeValueAsBytes(moveForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
        )
                .andExpect(status().isOk())
                .andReturn();
        MoveDto moveDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MoveDto.class);
        assertNotNull(moveDto);
        assertEquals(moveForm.getBoardRowNumber(), moveDto.getBoardRowNumber());
        assertEquals(moveForm.getBoardColumnNumber(), moveDto.getBoardColumnNumber());
        assertEquals(moveForm.getMoveNumber(), moveDto.getMoveNumber());
        assertEquals(gameDto, moveDto.getGame());
        return moveDto;
    }
}
