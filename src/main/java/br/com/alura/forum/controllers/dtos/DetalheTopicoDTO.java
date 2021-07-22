package br.com.alura.forum.controllers.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class DetalheTopicoDTO extends TopicoDTO {
	
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDTO> respostas = new ArrayList<RespostaDTO>();
	
	public DetalheTopicoDTO(Topico topico) {
		super(topico);
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDTO> getRespostas() {
		return respostas;
	}

}
