package control;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

/**
 * Classe principal
 * 
 * @author Thiago Dal Pont
 */
public class Principal {

    /**
     * @param args
     */
    public static void main(String[] args) {

	// criaProduto(); // criaUsuarios(); // criaTipos(); //
	// criaInteracoes();

	/*
	 * while (true) {
	 * 
	 * try { System.in.read(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * recomendar(10, ""); }
	 */

	try {
	    // Mongo mongo = new Mongo("pibic", "interacao");
	    //
	    // List<Document> lista = new ArrayList<Document>();
	    //
	    // lista.add(new Document("tipo", 1));
	    // lista.add(new Document("mac", "0"));
	    // mongo.insere(lista);
	    // mongo.insere(new Document("MAC", "Teste"));
	    //
	    // Document document = new Document();
	    // document.append("tipo", 1);
	    // document.append("MAC", "48:71:59:B5:6A:E6");
	    // document.append("major", 10);
	    // document.append("mijor", 11);
	    // document.append("rssi", -2.3);
	    //
	    // mongo.insere(document);
	    // System.out.println("Procura:" + mongo.procura(new Document("abc",
	    // 123)));
	    // System.out.println("Procura:" + mongo.procura(new
	    // Document("abcd", 123.0)));
	    // System.out.println("Lista: " + mongo.listaRegistros());
	    // mongo.remove(new Document("abc", 123));
	    //
	    // System.out.println(mongo.listaRegistros());
	    // mongo.removeTodos();
	    // System.out.println(mongo.listaRegistros());
	    //
	    // mongo.fechaConexao();
	    //
	    // System.out.println("Game Finish");
	} catch (Exception e) {
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	}

    }

}
