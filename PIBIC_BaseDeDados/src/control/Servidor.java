/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.bson.Document;

import model.Usuario;
import oracle.sql.DATE;

/**
 * @author Thiago Dal Pont
 */
@Path("/")
public class Servidor {

    private Recomendacao recomendacao = new Recomendacao();
    private Mongo mongodb = new Mongo("pibic", "interacao");

    @GET
    @Path("/{user_id}/{mac}")
    @Produces("text/plain")
    public String teste(@PathParam("user_id") int idUsuario1, @PathParam("mac") int idUsuario2) {
	long v2, v1 = System.currentTimeMillis();
	String st = recBaseada(idUsuario1);
	// return "Ret: " + recomendacao.recomendar(idUsuario, mac);
	// return rec(idUsuario1, idUsuario2);
	v2 = System.currentTimeMillis();

	System.out.println("Time: " + (v2 - v1) + "ms");
	return st;
    }

    // http://localhost:8080/PIBIC_BaseDeDados/baserec/mongo?user=1&tipo=1&major=10&minor=3&rssi=-3.0
    @GET
    @Path("/mongo/")
    public String novainteracao(@QueryParam("user") Integer user_id, @QueryParam("tipo") Integer tipo, @QueryParam("mac") String mac,
		    @QueryParam("major") Integer beacon_major, @QueryParam("minor") Integer beacon_minor, @QueryParam("rssi") Float beacon_rssi) {
	String message = "OK";
	Document registro = new Document();

	try {
	    registro.append("usuario", user_id);
	    registro.append("data", new Date().toString());
	    registro.append("mac", mac);

	    switch (tipo) {
	    case Mongo.BEACON:
		registro.append("tipo", Mongo.BEACON);
		registro.append("major", beacon_major);
		registro.append("minor", beacon_minor);
		registro.append("rssi", beacon_rssi);

		break;
	    case Mongo.SENSOR:
		registro.append("tipo", Mongo.SENSOR);

		break;
	    case Mongo.TAG_NFC:
		registro.append("tipo", Mongo.BEACON);
		break;
	    }

	    mongodb.insere(registro);

	    message = registro.toJson();

	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    message = e.getMessage();
	}

	return message;
    }

    @GET
    @Path("recbase/{user_id}")
    @Produces("text/plain")
    public String recBaseada(@PathParam("user_id") int idPessoa) {
	try {
	    if (idPessoa > recomendacao.getController().list(Usuario.class).size()) {
		return "No user found";
	    }

	    long v2, v1 = System.currentTimeMillis();

	    Map<Integer, Integer> hashPessoa = criaHashFromPessoa(idPessoa);

	    // Lista de maps de produtos de cada usuário
	    List<Double> listaAngulos = new ArrayList<>();

	    String sql = "SELECT DISTINCT R1.ID_USUARIO " + "FROM ( " + "SELECT ID_USUARIO, ID_PRODUTO " + "FROM interacao " + "ORDER BY 1) AS R1, "
			    + "(SELECT ID_USUARIO, ID_PRODUTO " + "FROM interacao " + "WHERE ID_USUARIO = #1) AS R2 "
			    + "WHERE R1.ID_USUARIO != R2.ID_USUARIO AND R1.ID_PRODUTO = R2.ID_PRODUTO " + "ORDER BY 1 ";
	    List<Object> list = new ArrayList<Object>();
	    list.add(idPessoa);

	    // Procura por todas as pessoas que têm pelo menos um item em comum.
	    list = recomendacao.getController().query(sql, list); // lista de
								  // ids

	    // Map<Integer, String> map = new HashMap<>();
	    if (list == null || list.size() <= 0) {
		return Util.toJson(null);
	    }
	    // Varre a lista de pessoas
	    for (int i = 0; i < list.size(); i++) {
		int x = Integer.valueOf(list.get(i).toString());

		listaAngulos.add(cosseno(hashPessoa, criaHashFromPessoa(x)));
	    }
	    Random rand = new Random();
	    int numMatchs = Math.abs(rand.nextInt() % 5) + 1;
	    int[] matchs = new int[numMatchs];
	    double max = 0;
	    int i, index = 0;

	    // Seleciona os maiores angulos
	    for (int j = 0; j < numMatchs; j++) {
		max = listaAngulos.get(0);

		index = 0;

		for (i = 1; i < listaAngulos.size(); i++) {
		    if (max < listaAngulos.get(i)) {
			max = listaAngulos.get(i);
			index = i;
		    }
		}

		matchs[j] = Integer.valueOf(list.get(index).toString());

		System.out.println("Match j: " + matchs[j] + "\tCos: " + listaAngulos.get(j));
		;
		listaAngulos.set(index, -1.0);
	    }

	    list = new ArrayList<>();

	    for (int j = 0; j < numMatchs; j++) {
		if (matchs[j] > 0) {
		    List<Object> result = recomendacao.recomendar(idPessoa, matchs[j]);
		    if (result != null)
			list.addAll(result);
		}
	    }

	    v2 = System.currentTimeMillis();

	    System.out.println((v2 - v1) + "ms");
	    String st = Util.toJson(list);

	    return st;
	} catch (Exception e) {
	    e.printStackTrace();
	    return e.getMessage();
	}
    }

    @GET
    @Path("/criatipo")
    public String criaTipos() {
	try {
	    recomendacao.criaTipos();

	    return "Tipos criados com sucesso!";
	} catch (Exception e) {
	    return "Erro: " + e.getMessage();
	}
    }

    // Retorna o cosseno do ângulo entre dois usuário.
    public String rec(Integer idUsuario1, Integer idUsuario2) {

	try {
	    // Usuario user = (Usuario)
	    // recomendacao.getController().list(Usuario.class);

	    Map<Integer, Integer> hash1 = criaHashFromPessoa(idUsuario1);
	    Map<Integer, Integer> hash2 = criaHashFromPessoa(idUsuario2);

	    Double cos = cosseno(hash1, hash2);

	    // System.out.println(str);

	    return "cos(0): " + cos.toString();
	} catch (Exception e) {
	    return e.getMessage();
	}
    }

    public String rec(Integer idUsuario) {

	try {
	    double cosProx = 0;
	    int idProx = 0;

	    Map<Integer, Integer> hash1 = criaHashFromPessoa(idUsuario);
	    Map<Integer, Integer> hashN;

	    for (Object obj : recomendacao.getController().list(Usuario.class)) {
		int id = ((Usuario) obj).getId_Usuario();
		hashN = criaHashFromPessoa(id);
		double cos0 = cosseno(hash1, hashN);

		if (cos0 > cosProx && idUsuario != id) {
		    cosProx = cos0;
		    idProx = id;
		}
	    }
	    return idProx + ":" + cosProx;

	} catch (Exception e) {
	    return e.getMessage() + "\n";
	}
    }

    public Map<Integer, Integer> criaHashFromPessoa(int idUsuario) {

	Map<Integer, Integer> map = new HashMap<>();
	List<Object> list = new ArrayList<>();
	list.add(idUsuario);
	// Select de todos os produtos que o usuário interagiu
	String sql = "SELECT ID_PRODUTO FROM produto WHERE ID_PRODUTO IN ( SELECT ID_PRODUTO FROM interacao WHERE ID_USUARIO = #1 ORDER BY ID_PRODUTO);";

	List<Object> listaProdutos = recomendacao.getController().query(sql, list);

	if (listaProdutos == null) {
	    return null;
	}

	// Insere no HASH

	// TODO: corrigir erro no for - casting com int e obj
	for (int i = 0; i < listaProdutos.size(); i++) {
	    map.put((Integer) listaProdutos.get(i), 1); // (id, peso)
	}
	return map;
    }

    public double cosseno(Map<Integer, Integer> hashP1, Map<Integer, Integer> hashP2) {

	double somaNum = 0; // Soma do numerador na fórmula.
	double somaDen1 = 0, somaDen2 = 0; // Soma do denominador na fórmula.
	boolean contains = false;

	/**
	 * Popular o Hash
	 */

	// Iterações para cálculo do numerador e denominador.
	for (Integer key1 : hashP1.keySet()) {
	    if (hashP2.containsKey(key1)) {
		somaNum += hashP1.get(key1) * hashP2.get(key1);
		contains = true;
	    }

	    somaDen1 += Math.pow(hashP1.get(key1), 2);
	}

	// Caso tenha pelo menos um em comum, finaliza o cálculo.
	if (contains) {
	    // Cálculo da segunda parte do denominador
	    for (Integer key2 : hashP2.keySet()) {
		somaDen2 += Math.pow(hashP2.get(key2), 2);
	    }

	    return (somaNum / Math.sqrt(somaDen1 * somaDen2));
	} else {
	    return 0D;
	}
    }

    @GET
    @Path("/criaproduto")
    public String criaProdutos() {
	try {
	    recomendacao.criaProduto();
	    return "Produtos criados";
	} catch (Exception e) {
	    return "Erro: " + e.getMessage();
	}
    }

    @GET
    @Path("/criainteracao")
    public String criaInteracao() {
	try {
	    recomendacao.criaInteracoes();
	    return "Interacoes geradas com sucesso";
	} catch (Exception e) {
	    return "Erro: " + e.getMessage();
	}
    }

    // (@PathParam("user_id") int idUsuario, @PathParam("mac") String mac)
    @GET
    @Path("/criainteracao/{idUsuario}/{idProduto}")
    public String criaInteracao(@PathParam("idUsuario") int idUsuario, @PathParam("idProduto") int idProduto) {
	try {
	    // recomendacao.criaInteracoes();
	    return "Interacoes geradas com sucesso 2" + idUsuario + "\t" + idProduto;
	} catch (Exception e) {
	    return "Erro: " + e.getMessage();
	}
    }
}
