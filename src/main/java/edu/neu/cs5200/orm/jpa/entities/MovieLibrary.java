package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MovieLibrary {
	
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	
	private int id;
	private String name;
	
	@OneToMany(mappedBy="library",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Movie> movies;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

}
