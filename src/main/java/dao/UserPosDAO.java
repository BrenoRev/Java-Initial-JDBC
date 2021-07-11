package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import conexaojdbc.SingleConnection;
import model.Userposjava;

public class UserPosDAO {

	// CRIAÇÃO DA PERSISTÊNCIA DE DADOS NO BANCO DE DADOS
	
	private Connection connection;
	
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava) {
		// ADICIONA NO BANCO DE DADOS O COMANDO " SQL " QUE FOI DIGITADO
		try {
		String sql = "insert into userposjava (id,nome,email) values (?,?,?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		
		// DEFININDO O QUE VAI SER CADA INTERROGAÇÃO DO COMANDO
		// PRIMEIRO INDICA QUAL A INTERROGAÇÃO E DEPOIS O VALOR
		insert.setLong(1, userposjava.getId());
		insert.setString(2, userposjava.getNome());
		insert.setString(3, userposjava.getEmail());
		
		// PEDE PARA EXECUTAR O COMANDO
		insert.execute();
		
		// SALVA NO BANCO
		
		connection.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
