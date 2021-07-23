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
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repositories.CursoRepository;
import br.com.alura.forum.repositories.TopicoRepository;
import br.com.alura.forum.repositories.UsuarioRepository;

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
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private MockMvc mock;

	@Before
	public void setUp() throws Exception {
		path = new URI("/topicos");
		
		Usuario usuario1 = new Usuario();
		usuario1.setNome("Aluno");
		usuario1.setEmail("aluno@email.com");
		usuario1.setSenha("$2a$10$e0fvHjp0BoMVD0jMhyNzGOXaGODJVsu/5tz0.w1Z4ZI7utXZ06J9W");
		
		Usuario usuario2 = new Usuario();
		usuario1.setNome("Moderador");
		usuario1.setEmail("moderador@email.com");
		usuario1.setSenha("$2a$10$e0fvHjp0BoMVD0jMhyNzGOXaGODJVsu/5tz0.w1Z4ZI7utXZ06J9W");
		
		Curso springBoot = new Curso();
		Curso html5 = new Curso();

		springBoot.setNome("Spring Boot");
		springBoot.setCategoria("Programação");

		html5.setNome("HTML 5");
		html5.setCategoria("Front-end");
		
		Topico topico1 = new Topico("Bigode", "Error ao criar projeto", springBoot);
		Topico topico2 = new Topico("Bigorna", "Projeto não compila", springBoot);
		Topico topico3 = new Topico("Bigodinho", "Tag HTML", html5);
		
		usuarioRepository.save(usuario1);
		usuarioRepository.save(usuario2);
		
		cursoRepository.save(springBoot);
		cursoRepository.save(html5);
		
//		entityManager.persist(springBoot);
//		entityManager.persist(html5);
		
		topicoRepository.save(topico1);
		topicoRepository.save(topico2);
		topicoRepository.save(topico3);
		
//		entityManager.persist(topico1);
//		entityManager.persist(topico2);
//		entityManager.persist(topico3);
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
		request = MockMvcRequestBuilders.get("/topicos/{id}", Long.valueOf("1"));
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
