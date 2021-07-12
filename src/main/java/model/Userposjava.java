package model;

public class Userposjava {

	private Long id;
	private String nome;
	private String email;
	private Telefone telefone;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	@Override
	public String toString() {
		return "[ ID= " + id + " | Nome= " + nome + " | Email= " + email + " | " + telefone ;
	}
	
	
	
}
