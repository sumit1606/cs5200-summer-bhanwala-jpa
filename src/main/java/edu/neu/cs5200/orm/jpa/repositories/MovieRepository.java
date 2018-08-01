package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
	@Query("SELECT movie FROM Movie movie WHERE movie.title=:title")
	public Movie findMovieByTitle(
			@Param("title") String title);
}
