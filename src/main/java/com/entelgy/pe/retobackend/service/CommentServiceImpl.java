package com.entelgy.pe.retobackend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.entelgy.pe.retobackend.model.Comment;
import com.entelgy.pe.retobackend.model.ListarCommentsResponse;
import com.entelgy.pe.retobackend.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommentServiceImpl implements CommentService {

	ListarCommentsResponse response = new ListarCommentsResponse();

	@Autowired
	RestTemplate restTemplate;

	@Override
	public ListarCommentsResponse getComments() {
		ListarCommentsResponse response = new ListarCommentsResponse();
		List<String> list = new ArrayList<>();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			System.out.println("INCIO - CONSULTANDO SERVICIO GET COMMENTS");

			ResponseEntity<String> responseComments = restTemplate.getForEntity(Constants.URL_GET_COMMENTS,
					String.class);
			ObjectMapper mapper = new ObjectMapper();
			String responseBody = responseComments.getBody();
			List<Comment> commentsList = mapper.readValue(responseBody, new TypeReference<List<Comment>>() {
			});
			System.out.println("HTTP Response code: " + responseComments.getStatusCodeValue());
			System.out.println("HTTP Response: " + responseComments.getBody());
			System.out.println("FIN - CONSULTANDO SERVICIO GET COMMENTS");
			System.out.println("size: " + commentsList.size());

			System.out.println("transformando response");
			for (int i = 0; i < commentsList.size(); i++) {
				list.add(commentsList.get(i).getPostId() + "|" + commentsList.get(i).getId() + "|"
						+ commentsList.get(i).getEmail());
			}
			response.setData(list);

		} catch (HttpClientErrorException ex) {
			System.out.println("HTTP Error: " + ex.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return response;
	}
}
