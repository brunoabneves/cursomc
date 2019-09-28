package com.bruno.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bruno.cursomc.domain.enums.ClientType;
import com.bruno.cursomc.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(unique = true)  //Garante que o campo seja único
	private String email;
	private String cpfOrcnpj;
	private Integer type;    //internamente o tipo será armazenado como um inteiro
	
	@JsonIgnore
	private String password;
		
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<Address> adresses = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "PHONE")
	//Um CONJUNTO (não aceita repetições) de Strings
	private Set<String> phones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PROFILES")
	private Set<Integer> profiles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Request> requests = new ArrayList<>();
	
	public Client() {
		addPerfil(Profile.CLIENT);
	}
	
	public Client(Integer id, String name, String email, String cpfOrcnpj, ClientType type, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrcnpj = cpfOrcnpj;
		//se o tipo for nulo atribui-se nulo a esse campo, do contrário atibui-se o código
		this.type = (type==null) ? null : type.getCod();   //armazena somente o código
		this.password = password;
		addPerfil(Profile.CLIENT);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOrcnpj() {
		return cpfOrcnpj;
	}

	public void setCpfOrcnpj(String cpfOrcnpj) {
		this.cpfOrcnpj = cpfOrcnpj;
	}

	public ClientType getType() {
		return ClientType.toEnum(type);  //uso da classe de enum
	}

	public void setType(ClientType type) {
		this.type = type.getCod();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Profile> getPerfis() {
		return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Profile perfil) {
		profiles.add(perfil.getCod());
	}

	public List<Address> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}
	
	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
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
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
