package br.com.alura.forum.controllers.dtos;

public class ValidationErrorDTO {

	private String campo;
	private String erro;

	public ValidationErrorDTO(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}

}
