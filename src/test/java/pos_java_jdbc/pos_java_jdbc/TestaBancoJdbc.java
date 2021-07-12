package pos_java_jdbc.pos_java_jdbc;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import model.Userposjava;

public class TestaBancoJdbc {

	// TESTAR A CONEXÃO COM O BANCO DE DADOS COM JUNIT
	/*
	@Test
	public void initBanco() {
		SingleConnection.getConnection();
		UserPosDAO userPosDAO= new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setId(5L);
		userposjava.setNome("Eliane");
		userposjava.setEmail("elianemaria8858@gmail.com");
		
		userPosDAO.salvar(userposjava);
		
	
	}
	
	@Test
	public void initPesquisar() throws Exception {
		SingleConnection.getConnection();
		UserPosDAO userPosDAO= new UserPosDAO();
		userPosDAO.pesquisarTodos();
	}
	*/
	@Test
	public void initPesquisarUm() throws Exception {
		SingleConnection.getConnection();
		UserPosDAO userPosDAO= new UserPosDAO();
		userPosDAO.pesquisarUm(9L);
	}
}
