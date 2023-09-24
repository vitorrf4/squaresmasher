package com.store;

import com.store.controllers.UserController;
import com.store.models.User;
import com.store.repos.UserRepo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
	@MockBean private UserRepo repo;
	@Autowired @InjectMocks private UserController controller;

	@Test
	@DisplayName("GET /users success")
	public void whenGetUsers_thenSuccessCode() {
		User userToAdd1 = new User();
		User userToAdd2 = new User();

		given(repo.findAll()).willReturn(new ArrayList<>(List.of(userToAdd1, userToAdd2)));

		assertThat(controller.getAllUsers().getBody().size()).isEqualTo(2);
		assertThat(controller.getAllUsers().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("GET /users/1 success")
	public void whenGetUserById_thenSuccessCode() {
		User u1 = new User();
		u1.setName("test");

		given(repo.findById(1L)).willReturn(Optional.of(u1));

		assertThat(controller.getUserById(1L).getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("GET /users/1 failure")
	public void whenGetUserById_thenNotFound() {
		assertThat(controller.getUserById(1L).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("POST /users/{id} success")
	public void whenAddUser_thenSuccess() throws Exception {
		User u1 = new User();
		u1.setId(1L);
		u1.setName("teste");
		u1.setPassword("test password");

		given(repo.save(u1)).willReturn(u1);
		ResponseEntity<User> expectedEntity = ResponseEntity.created(new URI("/users/1")).body(u1);

		assertThat(controller.addUser(u1).getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(controller.addUser(u1)).isEqualTo(expectedEntity);
	}

	@Test
	@DisplayName("POST /users failure")
	public void whenAddUser_thenFailure() throws URISyntaxException {
		assertThat(controller.addUser(new User()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
