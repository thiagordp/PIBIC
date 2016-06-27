/**
 * 
 */
package model;

import java.sql.Timestamp;

/**
 * @author Thiago Dal Pont
 *
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rest")
public class Rest {

	@Column(name = "idUsuario", nullable = false)
	private int idUsuario = 0;

	@Column(name = "uuid", nullable = false)
	private String uuid = "";

	@Column(name = "major", nullable = false)
	private int major = 0;

	@Column(name = "minor", nullable = false)
	private int minor = 0;

	@Column(name = "potencia", nullable = false)
	private float potencia = 0;

	@Id
	@Column(name = "dataReq", nullable = false)
	private Timestamp dataReq = null;

	public Rest(int idUsuario, String uuid, int major, int minor, float potencia, Timestamp dataReq) {
		this.idUsuario = idUsuario;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.potencia = potencia;
		this.dataReq = dataReq;
	}

	// -------------- GETTERS AND SETTERS ---------------- //

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public float getPotencia() {
		return potencia;
	}

	public void setPotencia(float potencia) {
		this.potencia = potencia;
	}

	public Timestamp getDataReq() {
		return dataReq;
	}

	public void setDataReq(Timestamp dataReq) {
		this.dataReq = dataReq;
	}
}