package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;


public interface MovieLibraryRepository extends CrudRepository<MovieLibrary, Integer>{

	@Query("SELECT mlib FROM MovieLibrary mlib WHERE mlib.name=:name")
	public MovieLibrary findMovieLibraryByName(
			@Param("name") String name);
	
}
