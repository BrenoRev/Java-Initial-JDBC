package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;

import org.junit.Test;

import dao.UserPosDAO;
import model.Telefone;
import model.Userposjava;

public class TestaBancoJdbc {

	// TESTAR A CONEXÃO COM O BANCO DE DADOS COM JUNIT
	
	@Test
	public void initBanco() {
		UserPosDAO userPosDAO= new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setNome("Brenda Yasmin");
		userposjava.setEmail("Yasmin@gmail.com");
		
		userPosDAO.salvar(userposjava);
		
	
	}
	
	@Test
	public void initPesquisar() throws Exception {
		UserPosDAO userPosDAO= new UserPosDAO();
		userPosDAO.pesquisarTodos();
	}
	
	@Test
	public void initPesquisarUm() throws Exception {
		UserPosDAO userPosDAO= new UserPosDAO();
		userPosDAO.pesquisarUm(3L);
	}
	
	@Test
	public void initAtualizar() {
		UserPosDAO userPosDAO= new UserPosDAO();
		try {
			// ID DO USUARIO E O NOVO NOME COMO PARAMETRO
			userPosDAO.atualizar(3L, "Breno");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void initDeletar() {
		// DELETAR O USUARIO SE NÃO TIVER NENHUM TELEFONE VINCULADO A ELE
		UserPosDAO userPosDAO = new UserPosDAO();
		try {
			userPosDAO.deletarUsuario(5L);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@Test
	public void initTelefone() {
		UserPosDAO userPosDAO= new UserPosDAO();
		Telefone telefone = new Telefone();
		telefone.setNumero("8858-2992");
		telefone.setTipo("Fixo");
		telefone.setUsuario(10L);
		userPosDAO.salvarTelefone(telefone);
	}

	@Test
	public void initDeleteUserFone() {
		UserPosDAO userPosDAO = new UserPosDAO();
		try {
			userPosDAO.deletarUsuarioTelefone(10L);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


