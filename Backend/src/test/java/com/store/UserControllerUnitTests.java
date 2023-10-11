package com.store;

import com.store.controllers.UserController;
import com.store.models.User;
import com.store.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
class UserControllerUnitTests {
	@MockBean private UserService service;
	@Autowired private UserController controller;

	@Nested
	class GetTests {
		@Test @DisplayName("GET /users - Ok")
		public void whenGetUsers_thenSuccess() {
			given(service.getAllUsers()).willReturn(new ArrayList<>(List.of(new User(), new User())));

			assertThat(controller.getAllUsers().getStatusCode()).isEqualTo(HttpStatus.OK);
		}

		@Test @DisplayName("GET /users/1 - Ok")
		public void whenGetUserById_thenSuccess() {
			User user1 = new User();
			user1.setName("test name");

			given(service.getUser(1L)).willReturn(Optional.of(user1));

			assertThat(controller.getUserById("1").getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(controller.getUserById("1").getBody()).isEqualTo(user1);
		}

		@Test @DisplayName("GET /users/1 - NotFound")
		public void whenGetUserById_thenNotFound() {
			assertThat(controller.getUserById("1").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}

	}

	@Nested
	class PostTests {
		@Test @DisplayName("POST /users/1 - Created")
		public void whenAddUser_thenCreated() throws Exception {
			User user1 = new User();
			user1.setId(1L);
			user1.setName("test name");
			user1.setPassword("test password");

			given(service.createUser(user1)).willReturn(user1);
			ResponseEntity<User> expectedEntity = ResponseEntity.created(new URI("/users/1")).body(user1);

			assertThat(controller.createUser(user1).getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(controller.createUser(user1)).isEqualTo(expectedEntity);
		}

		@Test @DisplayName("POST /users - BadRequest")
		public void whenAddUser_thenBadRequest() {
			given(service.isUserInvalid(new User())).willReturn(true);
			assertThat(controller.createUser(new User()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}

		@Test @DisplayName("POST /users - Conflict")
		public void whenAddUser_thenConflict()  {
			User u1 = new User();
			u1.setName("conflict");
			User u2 = new User();
			u2.setName("conflict");
			u2.setPassword("test passwor");

			given(service.getUser(1L)).willReturn(Optional.of(u1));

			assertThat(controller.createUser(u2).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		}
	}

	@Nested
	class PutTests {
		@Test @DisplayName("PUT /users/1 - Ok")
		public void whenPutUser_thenSuccess() {
			User u1 = new User();
			u1.setName("test name");
			u1.setPassword("test password");

			User modifiedUser = new User();
			modifiedUser.setName("another name");
			modifiedUser.setPassword("test password");

			given(service.changeUser(u1)).willReturn(modifiedUser);

			assertThat(controller.changeUser(u1).getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(controller.changeUser(u1).getBody()).isEqualTo(modifiedUser);
		}

		@Test @DisplayName("PUT /users/1 - NotFound")
		public void whenPutUser_thenNotFound() {
			assertThat(controller.changeUser(new User()).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}

		@Test @DisplayName("PUT /users/1 - BadRequest")
		public void whenPutUser_thenBadRequest() {
			given(service.isUserInvalid(new User())).willReturn(true);
			assertThat(controller.changeUser(new User()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}

	}

	@Nested
	class DeleteTests {
		@Test @DisplayName("DELETE /users/1 - NoContent")
		public void whenDeleteUser_thenNoContent() {
			given(service.deleteUser(1L)).willReturn(true);
			assertThat(controller.deleteUser(1L).getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		}

		@Test @DisplayName("DELETE /users/1 - NotFound")
		public void whenDeleteUser_thenNotFound() {
			assertThat(controller.deleteUser(1L).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}
	}

}
