/**
 * 
 */
package control;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Rest;

/**
 * @author Thiago Dal Pont
 *
 */
@Path("/servidor")
public class Servidor {
	private ModelController modelCtr = null;

	public Servidor() {
		modelCtr = new ModelController();
	}

	/**
	 * Requisição do usuário.
	 */
	@GET
	@Path("/{idUsuario}/{mac}/{major}/{minor}/{potencia}")
	@Produces("text/plain")
	public String getData(@PathParam("idUsuario") int idUsuario, @PathParam("mac") String mac,
			@PathParam("major") int major, @PathParam("minor") int minor, @PathParam("potencia") float potencia) {

		String resposta = "";

		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			Rest rest = new Rest(idUsuario, mac, major, minor, potencia, timestamp);

			modelCtr.insert(rest);
			resposta = consulta(mac);

		} catch (Exception e) {
			resposta = e.getMessage() + "\t" + e.getCause();
		} finally {
			if (modelCtr != null) {
				modelCtr.close();
			}
		}

		return resposta;
	}

	/**
	 * Consulta no banco, ao MAC indicado.
	 */
	private String consulta(String mac) {

		String sql = "SELECT id_produto, nome_produto, id_mac from produto WHERE id_mac = '" + mac + "';";

		String retorno = "";

		try {
			Random random = new Random();

			List<Object> query = modelCtr.query(sql);
			List<Object> listaProduto = new ArrayList<>();

			if (query.size() > 0) {
				for (int i = Math.abs(random.nextInt(query.size())); i < query
						.size(); i += Math.abs(random.nextInt(query.size() - i)) + 1) {
					listaProduto.add(query.get(i));
				}

				retorno = toJson(listaProduto);
			} else {
				retorno = "{}";
			}
		} catch (Exception e) {
			retorno = e.getMessage();
		}

		return retorno;
	}

	/**
	 * Converte o array de objetos para uma string JSON
	 */
	private String toJson(List<Object> produtos) {

		String json = "{";
		if (produtos != null) {
			for (int i = 0; i < produtos.size(); i++) {
				Object[] obj = (Object[]) produtos.get(i);

				for (int j = 0; j < 2; j++) {
					json += "\"" + obj[j] + "\"";

					if (j == 0) {
						json += ":";
					}
				}

				if (i < produtos.size() - 1) {
					json += ",";
				}
			}

			json += "}";
		}

		return json;
	}
}
