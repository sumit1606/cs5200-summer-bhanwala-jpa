package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

}
