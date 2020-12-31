package com.entelgy.pe.retobackend.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import com.entelgy.pe.retobackend.model.ListarCommentsResponse;

@SpringBootTest
public class CommentServicelTest {

	@Autowired
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private CommentService commentService;

	ListarCommentsResponse responseServiceMock;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void init() {

		String responseCommentsMock = "[\r\n" + "  {\r\n" + "    \"postId\": 1,\r\n" + "    \"id\": 1,\r\n"
				+ "    \"name\": \"id labore ex et quam laborum\",\r\n" + "    \"email\": \"Eliseo@gardner.biz\",\r\n"
				+ "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\r\n"
				+ "  },\r\n" + "  {\r\n" + "    \"postId\": 1,\r\n" + "    \"id\": 2,\r\n"
				+ "    \"name\": \"quo vero reiciendis velit similique earum\",\r\n"
				+ "    \"email\": \"Jayne_Kuhic@sydney.com\",\r\n"
				+ "    \"body\": \"est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et\"\r\n"
				+ "  }" + "]";

		mockServer = MockRestServiceServer.bindTo(restTemplate).build();
		mockServer.expect(requestTo("https://jsonplaceholder.typicode.com/comments")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(responseCommentsMock, MediaType.APPLICATION_JSON));

	}

	@Test
	public void test() throws Exception {

		responseServiceMock = new ListarCommentsResponse();
		List<String> data = new ArrayList<>();
		data.add("1|1|Eliseo@gardner.biz");
		data.add("1|2|Jayne_Kuhic@sydney.com");
		responseServiceMock.setData(data);

		ListarCommentsResponse responseActual = commentService.getComments();

		System.out.println("Size expected: " + data.size());
		System.out.println("Size actual: " + responseActual.getData().size());

		Assert.assertEquals(responseServiceMock.getData().size(), responseActual.getData().size());
		Assert.assertEquals(responseServiceMock.getData(), responseActual.getData());
	}

}
