package com.entelgy.pe.retobackend.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.entelgy.pe.retobackend.model.ListarCommentsResponse;
import com.entelgy.pe.retobackend.service.CommentService;

@WebMvcTest(controllers = CommentController.class)
@ActiveProfiles("test")
class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CommentService commentService;

	ListarCommentsResponse responseService;

	@BeforeEach
	void setUp() {
		responseService = new ListarCommentsResponse();
		List<String> data = new ArrayList<>();
		data.add("1|1|Eliseo@gardner.biz");
		data.add("1|2|Jayne_Kuhic@sydney.com");
		data.add("1|3|Nikita@garfield.biz|");
		responseService.setData(data);
	}

	@Test
	void getComments() throws Exception {

		given(commentService.getComments()).willReturn(responseService);

		final ResultActions result = this.mockMvc.perform(post("/comments"));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.data.size()").value(responseService.getData().size()));
	}

}
