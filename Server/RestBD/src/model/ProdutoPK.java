/**
 * 
 */
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Thiago Dal Pont
 *
 */
@Embeddable
public class ProdutoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_mac", nullable = false)
	private String idMac = "";

	@Column(name = "id_produto")
	private Integer idProduto = 0;

	public ProdutoPK() {
		idMac = "";
		idProduto = 0;
	}

	public ProdutoPK(String idMac, Integer idProduto) {
		this.idMac = idMac;
		this.idProduto = idProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMac == null) ? 0 : idMac.hashCode());
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoPK other = (ProdutoPK) obj;
		if (idMac == null) {
			if (other.idMac != null)
				return false;
		} else if (!idMac.equals(other.idMac))
			return false;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		return true;
	}

	public String getIdMac() {
		return idMac;
	}

	public void setIdMac(String idMac) {
		this.idMac = idMac;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

}
