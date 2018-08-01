package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;

public interface DirectorRepository extends CrudRepository<Director, Integer>  {
	@Query("SELECT d FROM Director d WHERE d.firstName=:first AND d.lastName=:last")
	public Director findDirectorByName(
			@Param("first") String firstName, 
			@Param("last") String lastName);
}
