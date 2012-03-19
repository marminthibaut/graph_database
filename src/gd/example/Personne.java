package gd.example;

import java.io.Serializable;

/**
 * 
 * POJO entity
 * 
 * @author thibaut
 * 
 */
public class Personne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1620089226222021068L;

	/**
	 * Constructeur standard sans paramètre
	 */
	public Personne() {
	}

	@SuppressWarnings("javadoc")
	public Personne(Integer id) {
		super();
		this.id = id;
	}

	private Integer id;
	private String nom;
	private String prenom;
	private Integer numero;
	private String rue;

	/**
	 * @return the id
	 */

	public Integer getId() {
		return id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * @param rue
	 *            the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Personne [" + (id != null ? "id=" + id + ", " : "")
				+ (nom != null ? "nom=" + nom + ", " : "")
				+ (prenom != null ? "prenom=" + prenom + ", " : "")
				+ (numero != null ? "numero=" + numero + ", " : "")
				+ (rue != null ? "rue=" + rue : "") + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
