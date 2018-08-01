package edu.neu.cs5200.orm.jpa.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Actor;

public interface ActorRepository extends CrudRepository<Actor, Integer> {

	@Query("SELECT a FROM Actor a WHERE a.firstName=:first AND a.lastName=:last")
	public Actor findActorByName(
			@Param("first") String firstName, 
			@Param("last") String lastName);
}