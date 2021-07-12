package pos_java_jdbc.pos_java_jdbc;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import model.Userposjava;

public class TestaBancoJdbc {

	// TESTAR A CONEXÃO COM O BANCO DE DADOS COM JUNIT
	
	@Test
	public void initBanco() {
		UserPosDAO userPosDAO= new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setId(5L);
		userposjava.setNome("Eliane");
		userposjava.setEmail("elianemaria8858@gmail.com");
		
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
		userPosDAO.pesquisarUm(9L);
	}
	
	@Test
	public void initAtualizar() {
		UserPosDAO userPosDAO= new UserPosDAO();
		try {
			Userposjava userposjava = userPosDAO.pesquisarUm(3L);
			userposjava.setNome("Nome Mudado com o método atualizar");
			userPosDAO.atualizar(3L, "Breno");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
		
}
