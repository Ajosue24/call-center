package com.almundo.callcenter;

import com.almundo.callcenter.entity.Roles;
import com.almundo.callcenter.entity.Users;
import com.almundo.callcenter.service.GenerateDataFaker;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests {


	@Autowired
	@Test
	public void contextLoads() {
		for (int i = 0; i < 10; i++) {
			taskExecutor.execute(() -> {
				dispatchCall();
			});
		}
	}

	/**
	 * Atributo responsable que se ejecuten las tareas
	 */
	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private GenerateDataFaker generateDataFaker;
	/**
	 * Atributo tipo Queue para tener las colas de las llamadas en curso
	 */
	private Queue<Users> progressCall = new PriorityBlockingQueue<>();
	/**
	 * Atributo tipo lista que almacena Lista de usuarios del call-center
	 */
	private List<Users> usersList = new ArrayList<>();


	@Test
	public void generateFakerUsers() {
		Faker faker = new Faker();
		Users user = new Users(1, faker.name().firstName(), faker.name().lastName(), faker.bothify("????##@gmail.com"), new Roles());
		user.getEmail();
	}

	@Test
	public void dispatchCall() {
		try {
			//validamos que haya usuarios disponibles
			Users users = validateOperatorsAvailable();
			//numero aleatorio entre 5 y 10
			Integer timeCall = new Random().ints(5, 11).findFirst().getAsInt();
			if (users != null) {
				//Duracion de la llamada un tiempo aleatorio entre 5 y 10 segundos
				Thread.sleep(1000 * timeCall);
				progressCall.remove(users);
			} else {
				//no hay operadores disponibles lo dejamos en espera
				Thread.sleep(5 * 1000);
				dispatchCall();
			}

		} catch (InterruptedException e) {

		}
	}


	private Users validateOperatorsAvailable() {
		try {
			usersList = generateDataFaker.getEmployees("OPERADOR");

			usersList.removeAll(progressCall);

			if (usersList.isEmpty()) {
				//Usuarios de segundo nivel
				usersList = generateDataFaker.getEmployees("SUPERVISOR");
				usersList.removeAll(progressCall);
				if (usersList.isEmpty()) {
					//Usuarios de 3er nivel
					usersList = generateDataFaker.getEmployees("DIRECTOR");
					usersList.removeAll(progressCall);
					if (usersList.isEmpty()) {
						Users user = usersList.get(new Random().ints(0, usersList.size()).findFirst().getAsInt());
						progressCall.add(user);
						return user;
					} else {
						return null;
					}
				} else {
					Users user = usersList.get(new Random().ints(0, usersList.size()).findFirst().getAsInt());
					progressCall.add(user);
					return user;
				}

			} else {
				Users user = usersList.get(new Random().ints(0, usersList.size()).findFirst().getAsInt());
				progressCall.add(user);
				return user;
			}
		} catch (Exception e) {
			return null;
		}

	}
}
