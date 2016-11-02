/**
 * 
 */
package control;

import java.util.List;

/**
 * @author Thiago
 *
 */
public interface IController {
	public void insert(Object object);

	public void update(Object object);

	public void delete(Object object);

	public List<?> list(Class<?> iClass);

	public Object getByCode(Class<?> iClass, Integer id);

	public List<Object> query(String sql, List<Object> parametros);
}
