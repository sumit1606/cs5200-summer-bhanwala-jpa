package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Director extends Person implements Serializable {

	private int OSCARWINS;

	@ManyToMany
	@JoinTable(name = "Movie2Director", joinColumns = @JoinColumn(name = "Director_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "Movie_id", referencedColumnName = "id"))
	private List<Movie> moviesDirected;

	public int getOSCARWINS() {
		return OSCARWINS;
	}

	public void setOSCARWINS(int oSCARWINS) {
		OSCARWINS = oSCARWINS;
	}

	public List<Movie> getMoviesDirected() {
		return moviesDirected;
	}

	public void setMoviesDirected(List<Movie> moviesDirected) {
		this.moviesDirected = moviesDirected;
	}

}
