package com.bruno.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private double price;
	
	
	@ManyToMany
	//DEFININDO A TABELA QUE FARÁ O "MUITOS PARA MUITOS" NO BD
	@JoinTable(name = "PRODUCT_CATEGORY",
		joinColumns = @JoinColumn(name = "product_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	@JsonIgnore
	private List<Category> categories = new ArrayList<>(); 
	
	//Associação com a classe de associação
	@JsonIgnore
	@OneToMany(mappedBy = "id.product")
	private Set<RequestItem> items = new HashSet<>();
	
	public Product() {
		
	}

	public Product(Integer id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	@JsonIgnore	//para não serializar essa lista de pedidos. Referenica cíclica
	public List<Request> getRequests(){
		List<Request> list = new ArrayList<>();
		
		/*Para cada item de pedido "x" que existir na lista de itens, 
		será adicionado o pedido associado a ele, na lista */
		for(RequestItem x : items) {
			list.add(x.getRequest());
		}
		return list;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Set<RequestItem> getItems() {
		return items;
	}

	public void setItems(Set<RequestItem> items) {
		this.items = items;
	}

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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
