package com.store;

import com.store.controllers.UserController;
import com.store.models.User;
import com.store.repos.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
class UserControllerTest {
	@MockBean private UserRepository repo;
	@Autowired private UserController controller;

	@Test @DisplayName("GET /users - Success")
	public void whenGetUsers_thenSuccess() {
		User userToAdd1 = new User();
		User userToAdd2 = new User();

		given(repo.findAll()).willReturn(new ArrayList<>(List.of(userToAdd1, userToAdd2)));

		assertThat(controller.getAllUsers().getBody().size()).isEqualTo(2);
		assertThat(controller.getAllUsers().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test @DisplayName("GET /users/1 - Success")
	public void whenGetUserById_thenSuccess() {
		User user1 = new User();
		user1.setName("test name");

		given(repo.existsById(1L)).willReturn(true);
		given(repo.findById(1L)).willReturn(Optional.of(user1));

		assertThat(controller.getUserById(1L).getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(controller.getUserById(1L).getBody()).isEqualTo(Optional.of(user1));
	}

	@Test @DisplayName("GET /users/1 - NotFound")
	public void whenGetUserById_thenNotFound() {
		assertThat(controller.getUserById(1L).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test @DisplayName("POST /users/1 - Created")
	public void whenAddUser_thenCreated() throws Exception {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("test name");
		user1.setPassword("test password");

		given(repo.save(user1)).willReturn(user1);
		ResponseEntity<User> expectedEntity = ResponseEntity.created(new URI("/users/1")).body(user1);

		assertThat(controller.addUser(user1).getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(controller.addUser(user1)).isEqualTo(expectedEntity);
	}

	@Test @DisplayName("POST /users - BadRequest")
	public void whenAddUser_thenBadRequest() throws URISyntaxException {
		assertThat(controller.addUser(new User()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test @DisplayName("POST /users - Conflict")
	public void whenAddUser_thenConflict() throws URISyntaxException {
		User u1 = new User();
		u1.setName("conflict");
		User u2 = new User();
		u2.setName("conflict");
		u2.setPassword("test passwor");

		given(repo.findByName("conflict")).willReturn(u1);

		assertThat(controller.addUser(u2).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test @DisplayName("PUT /users/1 - Success")
	public void whenPutUser_thenSuccess() {
		User u1 = new User();
		u1.setName("test name");
		u1.setPassword("test password");

		User modifiedUser = new User();
		modifiedUser.setName("another name");
		modifiedUser.setPassword("test password");

		given(repo.existsById(1L)).willReturn(true);
		given(repo.save(u1)).willReturn(modifiedUser);

		assertThat(controller.changeUser(1L, u1).getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(controller.changeUser(1L, u1).getBody()).isEqualTo(modifiedUser);
	}

	@Test @DisplayName("PUT /users/1 - NotFound")
	public void whenPutUser_thenNotFound() {
		assertThat(controller.changeUser(1L, new User()).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test @DisplayName("PUT /users/1 - NotFound")
	public void whenPutUser_thenBadRequest() {
		given(repo.existsById(1L)).willReturn(true);
		assertThat(controller.changeUser(1L, new User()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test @DisplayName("DELETE /users/1 - NoContent")
	public void whenDeleteUser_thenNoContent() {
		given(repo.existsById(1L)).willReturn(true);

		assertThat(controller.deleteUser(1L).getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test @DisplayName("DELETE /users/1 - NotFound")
	public void whenDeleteUser_thenNotFound() {
		assertThat(controller.deleteUser(1L).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
