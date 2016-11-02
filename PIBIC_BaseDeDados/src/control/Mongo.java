/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author Thiago
 *
 */
public class Mongo {

    public static final int BEACON = 1;
    public static final int TAG_NFC = 2;
    public static final int SENSOR = 3;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    /**
     * Construtor da classe
     * 
     * @param dbName
     *            Nome do banco de dados que será manipulado
     * @param collection
     *            Coleção que será manipulada.
     */
    public Mongo(String dbName, String collection) {
	mongoClient = new MongoClient();
	mongoDatabase = mongoClient.getDatabase(dbName);
	mongoCollection = mongoDatabase.getCollection(collection);
    }

    /************************************************
     * MÉTODOS PÚBLICOS *
     ************************************************/
    private void verificaInstancias() {

	if (mongoClient == null || mongoDatabase == null || mongoCollection == null) {
	    throw new NullPointerException("NullPointer\nVerifique se todos os parâmetros foram instanciados corretamente\nCliente: " + mongoClient
			    + "\tBanco:" + mongoDatabase + "\tColeção:" + mongoCollection);
	}
    }

    /************************************************
     * MÉTODOS PÚBLICOS *
     ************************************************/

    public void fechaConexao() {
	verificaInstancias();
	mongoClient.close();
    }

    public void insere(Document documento) {
	verificaInstancias();

	mongoCollection.insertOne(documento);

    }

    public void insere(List<Document> documentos) {
	verificaInstancias();

	mongoCollection.insertMany(documentos);
    }

    public List<Document> procura(String chave, String valor) {
	verificaInstancias();
	return procura(new Document(chave, valor));
    }

    public List<Document> procura(Document documento) {
	verificaInstancias();

	List<Document> documents = new ArrayList<>();

	FindIterable<Document> iterable = mongoCollection.find(documento);

	iterable.forEach(new Block<Document>() {
	    @Override
	    public void apply(final Document document) {
		documents.add(document);
	    }
	});

	return documents;
    }

    public List<Document> listaRegistros() {
	verificaInstancias();

	List<Document> documents = new ArrayList<>();

	FindIterable<Document> iterable = mongoCollection.find();

	iterable.forEach(new Block<Document>() {
	    @Override
	    public void apply(final Document document) {
		documents.add(document);
	    }
	});

	return documents;
    }

    public void remove(Document documento) {
	verificaInstancias();
	mongoCollection.deleteOne(documento);
    }

    public void removeTodos() {
	verificaInstancias();
	mongoCollection.deleteMany(new Document());
    }

    /************************************************
     * GETTERS E SETTERS *
     ************************************************/

    /**
     * @return the mongoClient
     */
    public MongoClient getMongoClient() {
	return mongoClient;
    }

    /**
     * @param mongoClient
     *            the mongoClient to set
     */
    public void setMongoClient(MongoClient mongoClient) {
	this.mongoClient = mongoClient;
    }

    /**
     * @return the mongoDatabase
     */
    public MongoDatabase getMongoDatabase() {
	return mongoDatabase;
    }

    /**
     * @param mongoDatabase
     *            the mongoDatabase to set
     */
    public void setMongoDatabase(MongoDatabase mongoDatabase) {
	this.mongoDatabase = mongoDatabase;
    }

    /**
     * @return the mongoCollection
     */
    public MongoCollection<Document> getMongoCollection() {
	return mongoCollection;
    }

    /**
     * @param mongoCollection
     *            the mongoCollection to set
     */
    public void setMongoCollection(MongoCollection<Document> mongoCollection) {
	this.mongoCollection = mongoCollection;
    }

}
