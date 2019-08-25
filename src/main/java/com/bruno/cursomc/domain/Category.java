package com.bruno.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/*Os objetos de uma classe Serializable podem ser convertidos 
em uma sequencia de bytes. Serve para os objetos serem 
gravados em arquivos, trafegar em redes etc. */

@Entity   //indica que esta classe é uma entidade do JPA (Mapeamento)
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //definindo a estratégia de geração automática dos id's
	private Integer id;
	private String name;	
	
	/* resolve o problema da referência cíclica. 
	 * Avisa a classe que pode serializar os produtos sem problemas*/
	@JsonManagedReference
	@ManyToMany(mappedBy = "categories")
	//Associação da Categoria com o Produto
	private List<Product> products = new ArrayList<>();
	
	public Category() {}

	public Category(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	/**hashcode e equals - operações para comparar 
    os objetos por valor e não pelo ponteiro de memória **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	

}
