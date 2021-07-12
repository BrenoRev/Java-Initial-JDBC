package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	// CRIA��O DA PERSIST�NCIA DE DADOS NO BANCO DE DADOS

	public void comandosSQL() {
		/*
		 
		 		// CRIA��O DA TABELA DE USUARIOS 
		-------------------------------------------------------------------------------------------------
		
		  CREATE TABLE public.userposjava
			(
    		nome character varying(255),
    		email character varying(255),
    		CONSTRAINT user_pk PRIMARY KEY (id),
			);
			
		**************************************************************************************************
		
		  		// CRIA��O DA TABELA DE TELEFONES DE USUARIOS IMPLEMENTANDO UMA FOREIGN KEY E UMA SEQUENCIA PARA OS IDS
		------------------------------------------------------------------------------------------------- 	 
		
		 CREATE TABLE IF NOT EXISTS telefoneuser( id bigint NOT NULL, numero
		 varchar(255) NOT NULL, tipo varchar(255) NOT NULL, usuariopessoa bigint NOT
		 NULL, -- CRIANDO UMA PRIMARY KEY CHAMADA TELEFONE_ID CONSTRAINT telefone_id
		 PRIMARY KEY(id), -- CRIANDO UMA CHAVE ESTRANGEIRA REFERENCIANDO A TABELA PAI
		 FOREIGN KEY(usuariopessoa) REFERENCES userposjava(id) );
		 
		**************************************************************************************************
				
				// CRIA��O DA SEQUENCIA DE AUTO INCREMENTO DE ID E IMPLEMENTA��O NA TABELA
		-------------------------------------------------------------------------------------------------
		
		 create SEQUENCE user_telefone_sequence INCREMENT 1 minvalue 1 maxvalue 9223372036854775807 start 1;
		 ALTER TABLE telefoneuser ALTER COLUMN id SET DEFAULT nextval('user_telefone_sequence'::regclass);
		 
		**************************************************************************************************
		  
				// NECESS�RIO UTILIZAR ESSE COMANDO SQL NO BANCO DE DADOS PARA AUTO INCREMENTAR OS ID
		-------------------------------------------------------------------------------------------------
		
	    CREATE SEQUENCE usersequence INCREMENT 1 minvalue 1 maxvalue
		9223372036854775807 start 1;
		ALTER TABLE userposjava ALTER COLUMN id SET DEFAULT nextval('usersequence'::regclass);
		
		**************************************************************************************************
		
		
		*/
	}

	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Userposjava userposjava) {
		// ADICIONA NO BANCO DE DADOS O COMANDO " SQL " QUE FOI DIGITADO

		try {
			String sql = "insert into userposjava (nome,email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);

			// DEFININDO O QUE VAI SER CADA INTERROGA��O DO COMANDO
			// PRIMEIRO INDICA QUAL A INTERROGA��O E DEPOIS O VALOR
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());

			// PEDE PARA EXECUTAR O COMANDO
			insert.execute();

			// SALVA NO BANCO

			connection.commit();

			// EM CASO DE ERRO DAR ROLLBACK EM TUDO QUE FOI FEITO
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void salvarTelefone(Telefone telefone) {
		
		try {
			// INSERIR UM TELEFONE A UM USUARIO PELO ID
			
			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();
			
		}catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Userposjava> pesquisarTodos() throws Exception {

		// INSTANCIAR UMA LISTA DE USERPOSJAVA
		List<Userposjava> lista = new ArrayList<Userposjava>();

		// COMANDO SQL DE BUSCA DE ID, NOME E EMAIL E TELEFONE DE TODOS
		
		String sql = "select userposjava.id, userposjava.nome, userposjava.email, telefoneuser.numero, telefoneuser.tipo "
				+ "FROM userposjava "
				+ "LEFT JOIN telefoneuser "
				+ "ON userposjava.id = telefoneuser.usuariopessoa";

		// PREPARANDO O COMANDO RECEBENDO COMO PARAMETRO O COMANDO SQL
		PreparedStatement comando = connection.prepareStatement(sql);

		// RESULTSET VAI RECEBER O CONTEUDO QUE O COMANDO SQL RETORNAR
		ResultSet resultado = comando.executeQuery();

		// ENQUANTO O RESULTSET TIVER LINHAS A SEREM LIDAS ELE VAI SE MANTER NO LOOP
		while (resultado.next()) {
			// INSTANCIAR UM OBJETO PARA ADICIONAR A LISTA
			Userposjava userposjava = new Userposjava();
			Telefone telefone = new Telefone();
			
			// ATRIBUIR O ID, NOME E EMAIL E TELEFONE PASSANDO COMO PARAMETRO A COLUNA
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			telefone.setNumero(resultado.getString("numero"));
			telefone.setTipo(resultado.getString("tipo"));
			userposjava.setTelefone(telefone);
			// ADICIONAR A LISTA O OBJETO INSTANCIADO E ATRIBUIDO
			lista.add(userposjava);
		}
		// RETORNAR QUANDO O M�TODO FOR CHAMADO O TOSTRING DE TODOS DA LISTA
		lista.forEach(x -> System.out.println(x.toString()));

		// RETORNAR A LISTA NA CHAMADA DO M�TODO
		return lista;
	}

	public Userposjava pesquisarUm(Long id) throws Exception {

		// INSTANCIAR UMA OBJETO DE USERPOSJAVA E TELEFONE
		Userposjava userposjava = new Userposjava();
		Telefone telefone = new Telefone();
		
		// COMANDO SQL DE BUSCA DE ID, NOME E EMAIL E TELEFONE POR ID

		String sql = "select userposjava.id, userposjava.nome, userposjava.email, telefoneuser.numero, telefoneuser.tipo "
				+ "FROM userposjava "
				+ "JOIN telefoneuser "
				+ "ON userposjava.id = telefoneuser.usuariopessoa WHERE userposjava.id = " + id;
		
		// PREPARA O COMANDO A SER EXECUTADO
		PreparedStatement comando = connection.prepareStatement(sql);

		// RECEBE OS RESULTADOS RETORNADO PELO COMANDO SQL
		ResultSet resultado = comando.executeQuery();

		// RETORNA 1 OU NENHUM
		while (resultado.next()) {
			// ATRIBUI AO OBJETO OS ATRIBUTOS PESQUISADOS PELA COLUNA
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			telefone.setNumero(resultado.getString("numero"));
			telefone.setTipo(resultado.getString("tipo"));
			userposjava.setTelefone(telefone);
			// IMPRIME AS INFORMA��ES DO USUARIO CASO EXISTA
			System.out.println(userposjava.toString());
		}

		// RETORNA O OBJETO INSTANCIADO
		return userposjava;
	}

	public void atualizar(Long id, String novoNome) {
		// RECEBE UM USUARIO E UM NOME NOVO COMO PARAMETRO PARA SER ATUALIZADO

		try {
			// MONTAGEM DO C�DIGO SQL PARA PESQUISA NO BANCO DE DADOS
			String sql = "UPDATE userposjava SET nome = ? WHERE id = " + id;

			// PREPARA O COMANDO A SER EXECUTA
			PreparedStatement statement = connection.prepareStatement(sql);

			// NA PRIMEIRA INTERROGA��O VAI SER O NOME NOVO DO USU�RIO
			statement.setString(1, novoNome);

			// EXECUTAR O COMANDO
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				// EM CASO DE ERRO DAR ROLLBACK NAS MUDAN�AS
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void deletarUsuario(Long id) {
		// DELETAR UM USUARIO CASO ELE * N�O * TENHA UM TELEFONE VINCULADO COMO CHAVE ESTRANGEIRA
		
		// COMANDO SQL A SER EXECUTADO
		String sql = "DELETE FROM userposjava WHERE id =" + id;
		try {
			// PREPARAR O COMANDO
			PreparedStatement statement = connection.prepareStatement(sql);
			// EXECUTAR E COMITAR O COMANDO
			statement.execute();
			connection.commit();
			// RETORNAR UMA MENSAGEM DE SUCESSO
			System.out.println("Usuario com o id " + id + " foi deletado com sucesso!");
		} catch (SQLException e) {
			try {
				// EM CASO DE ERRO DAR ROLLBACK NA TRANSA��O
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void deletarUsuarioTelefone(Long id) throws SQLException {
		// DELETAR UM USUARIO CASO ELE J� TENHA UM TELEFONE VINCULADO COMO CHAVE ESTRANGEIRA
		
		// PRIMEIRO NECESSITA DELETAR O TELEFONE PARA QUEBRAR A LIGA��O ENTRE TABELAS 
		String sqlFone = "DELETE FROM telefoneuser WHERE usuariopessoa =" + id;
		
		// DEPOIS EXCLUIR O USUARIO J� DESVINCULADO
		String sqlUser = "DELETE FROM userposjava WHERE id =" +id;
		
		try {
			// PRIMERO EXECUTA O COMANDO PRA DESVINCULAR A CHAVE ESTRANGEIRA
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
			// DEPOIS O COMANDO DE DELE��O
			statement = connection.prepareStatement(sqlUser);
			statement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			// EM CASO DE ERRO NO MEIO DO PROCESSO N�O REALIZAR A OPERA��O E DAR ROLLBACK
			connection.rollback();
			e.printStackTrace();
		}
		
	}
}
