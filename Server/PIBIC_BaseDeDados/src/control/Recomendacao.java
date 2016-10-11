package control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import model.Interacao;
import model.Produto;
import model.TipoInteracao;
import model.Usuario;

public class Recomendacao {

    private ModelController controller;

    public Recomendacao() {
	controller = new ModelController();
    }

    /**
     * <b>Criação da base de tipos</b>
     */
    public void criaTipos() {

	System.out.println("======== Criação de Tipos ========");

	@SuppressWarnings("unchecked")
	List<TipoInteracao> list = (List<TipoInteracao>) controller.list(TipoInteracao.class);

	if ((list != null) && (list.size() > 0)) {
	    return;
	}

	TipoInteracao interacao = new TipoInteracao(1, "Venda");
	controller.insert(interacao);

	interacao = new TipoInteracao(2, "Interesse");
	controller.insert(interacao);
    }

    public List<Object> recomendar(Integer idUsuario, Integer idUsuario2) {

	System.out.println("======== Recomendação ========");
	/**
	 * Consulta que procura por todos os produtos que o usuário indicado
	 * ainda não interagiu.
	 */
	String consultaSQL = "SELECT distinct ID_PRODUTO,DESCRICAO FROM produto WHERE ID_PRODUTO IN ( 	"
			+ "SELECT distinct ID_PRODUTO 	FROM interacao 	WHERE ID_USUARIO = #1 ORDER BY ID_PRODUTO) AND ID_PRODUTO NOT IN (SELECT distinct ID_PRODUTO FROM interacao 	WHERE ID_USUARIO = #2 ORDER BY ID_PRODUTO);";

	/**
	 * Lista de parâmetros, nesse caso, o id do usuário.
	 */
	List<Object> lista = new ArrayList<>();
	lista.add(idUsuario2); // origem
	lista.add(idUsuario); // destino

	/**
	 * Execução da consulta.
	 */
	lista = controller.query(consultaSQL, lista);

	// Caso não exista nenhum produto para recomendar.

	if (lista == null)
	    return null;
	/*
	 * /** Comprimento da lista e contador de número de itens para
	 * recomendar.
	 */
	int length = lista.size();
	int count = 0;

	/**
	 * Gerador de números aleatórios
	 */
	Random random = new Random(System.nanoTime());

	/**
	 * Lista de produtos que serão recomendados
	 */
	List<Object> produtos = new ArrayList<>();

	/**
	 * Loop para seleção aleatória de produtos.
	 */
	for (int i = random.nextInt(length); i < length && count < Util.MAX_REC; i++) {

	    produtos.add(lista.get(i));

	    i += random.nextInt(length - i);
	    count++;
	}

	return produtos;
    }

    /**
     * <h1>Criação da base de produtos</h1>
     */
    public void criaProduto() {

	System.out.println("======== Criação de Produtos ========");
	// Inicialização do gerador de números aleatórios.
	Random random = new Random();

	// Criação da base de produtos
	for (int i = 0; i < 1000; i++) {
	    Produto produto = new Produto(i, "Produto " + i, random.nextInt(100),
			    Math.round(1000F * (random.nextInt(3000)) / (random.nextInt(1500))) / 100F);

	    controller.insert(produto);
	}
    }

    /**
     * <b>Criação da base de usuários</b>
     */
    public void criaUsuarios() {
	System.out.println("======== Criação de usuários ========");
	Random random = new Random(System.currentTimeMillis());

	int size = controller.list(Usuario.class).size() + 1;
	for (int i = size; i < size + 50; i++) {
	    Usuario usuario = new Usuario();

	    usuario.setId_tipo(i);
	    usuario.setNome("Usuario " + i);
	    usuario.setCep(String.valueOf(random.nextInt(100000009)));
	    usuario.setCpf(String.valueOf(random.nextInt(999999999)));
	    usuario.setEndereco("Endereco " + i);
	    usuario.setRg(String.valueOf(random.nextInt(900000)));

	    controller.insert(usuario);
	}

    }

    public void insereInteracao(int idUsuario, int idProduto) {
	System.out.println("======== Inserção de nova interação ========");
    }

    public void criaInteracoes() {
	System.out.println("======== Criação de interações ========");

	Random random = new Random(System.nanoTime());

	for (int i = 0; i < 5; i++) {
	    try {

		@SuppressWarnings("unchecked")
		List<Usuario> lista = (List<Usuario>) controller.list(Usuario.class);
		int lengh = lista.size() + 1;
		int id = random.nextInt(lengh) + 1;
		Usuario usuario = (Usuario) controller.getByCode(Usuario.class, id);

		@SuppressWarnings("unchecked")
		List<TipoInteracao> lista2 = (List<TipoInteracao>) controller.list(TipoInteracao.class);
		@SuppressWarnings("unchecked")
		List<Interacao> lista3 = (List<Interacao>) controller.list(Interacao.class);

		int value = Math.abs(random.nextInt(20)) + lista3.size() + 2;

		for (int j = lista3.size() + 1; j < value; j++) {

		    if ((i + j) % 10 == 0) {
			Thread.sleep(10);
		    }

		    Produto produto = (Produto) controller.getByCode(Produto.class, random.nextInt(controller.list(Produto.class).size() + 1));

		    TipoInteracao tipo = (TipoInteracao) controller.getByCode(TipoInteracao.class, random.nextInt(lista2.size() - 1) + 1);
		    Date date = new Date(System.currentTimeMillis());
		    Interacao interacao = new Interacao(j, date, produto, usuario, tipo, random.nextInt(50), random.nextFloat() * 10.0F);

		    controller.insert(interacao);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * @return the controller
     */
    public ModelController getController() {
	return controller;
    }

    /**
     * @param controller
     *            the controller to set
     */
    public void setController(ModelController controller) {
	this.controller = controller;
    }

}
