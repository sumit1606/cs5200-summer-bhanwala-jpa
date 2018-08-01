package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Actor extends Person implements Serializable{

	private int OSCARNOMINATIONS;

	@ManyToMany
	@JoinTable(name = "Movie2Actor", joinColumns = @JoinColumn(name = "Actor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "Movie_id", referencedColumnName = "id"))
	private List<Movie> moviesActed;

	public int getOSCARNOMINATIONS() {
		return OSCARNOMINATIONS;
	}

	public void setOSCARNOMINATIONS(int oSCARNOMINATIONS) {
		OSCARNOMINATIONS = oSCARNOMINATIONS;
	}

	public List<Movie> getMoviesActed() {
		return moviesActed;
	}

	public void setMoviesActed(List<Movie> moviesActed) {
		this.moviesActed = moviesActed;
	}

	public void actedMovies(Movie movie) {
		if(this.moviesActed == null) {
			this.moviesActed = new ArrayList<>();
		}
		this.moviesActed.add(movie);
		if(movie.getMovieActor() == null) {
			movie.setMovieActor(new ArrayList<>());
		}
		if (!movie.getMovieActor().contains(this)) {
			movie.getMovieActor().add(this);
		}
	}
}
