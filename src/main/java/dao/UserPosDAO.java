package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
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

	public List<Userposjava> pesquisarTodos() throws Exception {

		// INSTANCIAR UMA LISTA DE USERPOSJAVA
		List<Userposjava> lista = new ArrayList<Userposjava>();

		// COMANDO SQL DE BUSCA DE ID, NOME E EMAIL
		String sql = "select id, nome, email from userposjava";

		// PREPARANDO O COMANDO RECEBENDO COMO PARAMETRO O COMANDO SQL
		PreparedStatement comando = connection.prepareStatement(sql);

		// RESULTSET VAI RECEBER O CONTEUDO QUE O COMANDO SQL RETORNAR
		ResultSet resultado = comando.executeQuery();

		// ENQUANTO O RESULTSET TIVER LINHAS A SEREM LIDAS ELE VAI SE MANTER NO LOOP
		while (resultado.next()) {
			// INSTANCIAR UM OBJETO PARA ADICIONAR A LISTA
			Userposjava userposjava = new Userposjava();

			// ATRIBUIR O ID, NOME E EMAIL PASSANDO COMO PARAMETRO A COLUNA
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			// ADICIONAR A LISTA O OBJETO INSTANCIADO E ATRIBUIDO
			lista.add(userposjava);
		}
		// RETORNAR QUANDO O M�TODO FOR CHAMADO O TOSTRING DE TODOS DA LISTA
		lista.forEach(x -> System.out.println(x.toString()));

		// RETORNAR A LISTA NA CHAMADA DO M�TODO
		return lista;
	}

	public Userposjava pesquisarUm(Long id) throws Exception {

		// INSTANCIAR UMA OBJETO DE USERPOSJAVA
		Userposjava userposjava = new Userposjava();

		// COMANDO SQL DE BUSCA DE ID, NOME E EMAIL POR ID
		String sql = "SELECT id, nome, email FROM userposjava WHERE id = " + id;

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

			// EXECUTAR TODO O COMANDO
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

	public void deletar(Long id) {
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
}
