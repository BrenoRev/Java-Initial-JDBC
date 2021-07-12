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
		userPosDAO.pesquisarUm(9L);
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
		UserPosDAO userPosDAO = new UserPosDAO();
		try {
			userPosDAO.deletar(5L);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
}
