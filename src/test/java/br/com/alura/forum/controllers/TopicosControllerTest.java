package br.com.alura.forum.controllers;

import java.net.URI;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
@ActiveProfiles("test")
public class TopicosControllerTest {

	private URI path;
	private MockHttpServletRequestBuilder request;
	private ResultMatcher expectedResult;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MockMvc mock;

	@Before
	public void setUp() throws Exception {
		path = new URI("/topicos");

		Curso springBoot = new Curso();
		Curso html5 = new Curso();

		springBoot.setNome("Spring Boot");
		springBoot.setCategoria("Programação");

		html5.setNome("HTML 5");
		html5.setCategoria("Front-end");
		
		Topico topico1 = new Topico("Dúvida", "Error ao criar projeto", springBoot);
		Topico topico2 = new Topico("Dúvida 2", "Projeto não compila", springBoot);
		Topico topico3 = new Topico("Dúvida 3", "Tag HTML", html5);
		
		entityManager.persist(springBoot);
		entityManager.persist(html5);
		
		entityManager.persist(topico1);
		entityManager.persist(topico2);
		entityManager.persist(topico3);
	}

	@Test
	public void testListaTopicos() throws Exception {
		Assert.assertNotNull(path);
		
		request = MockMvcRequestBuilders.get(path);
		expectedResult = MockMvcResultMatchers.status().isOk();
		
		String response = mock.perform(request).andExpect(expectedResult).andReturn().getResponse()
				.getContentAsString();

		Assert.assertNotNull(response);
		Assert.assertTrue(response.contains("content"));
	}

	@Test
	public void testDetalhar() throws Exception {
		request = MockMvcRequestBuilders.get("/topicos/1");
		expectedResult = MockMvcResultMatchers.status().isOk();
		
		String response = mock.perform(request).andExpect(expectedResult).andReturn().getResponse()
				.getContentAsString();

		Assert.assertNotNull(response);
		Assert.assertTrue(response.contains("NAO_RESPONDIDO"));
	}

	@Test
	public void testCadastrar() {
		Assert.assertNotNull(path);
	}

	@Test
	public void testAtualizar() {
		Assert.assertNotNull(path);
	}

	@Test
	public void testRemover() {
		Assert.assertNotNull(path);
	}

}
