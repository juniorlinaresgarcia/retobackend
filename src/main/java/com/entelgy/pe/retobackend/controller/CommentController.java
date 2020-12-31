package com.entelgy.pe.retobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.entelgy.pe.retobackend.model.ListarCommentsResponse;
import com.entelgy.pe.retobackend.service.CommentService;
import com.entelgy.pe.retobackend.util.Constants;

@RestController
public class CommentController {

	@Autowired
	CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@RequestMapping(value = Constants.BASE_PATH_LISTAR_COMMENTS, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ListarCommentsResponse> getComments() {
		ListarCommentsResponse response = commentService.getComments();
		return new ResponseEntity<ListarCommentsResponse>(response, HttpStatus.OK);
	}
}
