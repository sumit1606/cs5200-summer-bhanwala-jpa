package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;

	@ManyToMany(mappedBy = "moviesActed")
	private List<Actor> movieActor;

	@ManyToMany(mappedBy = "moviesDirected")
	private List<Director> movieDirector;

	@ManyToOne
	@JsonIgnore
	private MovieLibrary library;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Actor> getMovieActor() {
		return movieActor;
	}

	public void setMovieActor(List<Actor> movieActor) {
		this.movieActor = movieActor;
	}

	public List<Director> getMovieDirector() {
		return movieDirector;
	}

	public void setMovieDirector(List<Director> movieDirector) {
		this.movieDirector = movieDirector;
	}

	public MovieLibrary getLibrary() {
		return library;
	}

	public void setLibrary(MovieLibrary library) {
		this.library = library;
	}

}
