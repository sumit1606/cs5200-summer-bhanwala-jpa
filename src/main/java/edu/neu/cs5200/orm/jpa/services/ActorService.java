package edu.neu.cs5200.orm.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.daos.ActorDao;
import edu.neu.cs5200.orm.jpa.daos.MovieDao;
import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Movie;

@RestController
public class ActorService {

	@Autowired
	ActorDao actorDao;
	
	@Autowired
	MovieDao movieDao;
	
	@GetMapping("/api/actor")
	public List<Actor> findAllActor() {
		List<Actor> allActors = actorDao.findAllActor();
		return allActors;
	}
	
	@GetMapping("/api/actor/{aid}")
	public Optional<Actor> findActorById(
			@PathVariable("aid") int aid) {
		return actorDao.findActorById(aid);
	}
	
	
	@GetMapping("/api/actor/{aid}/movie")
	public List<Movie> findMovieByActorId(
			@PathVariable("aid") int aid) {
		Optional<Actor> A = actorDao.findActorById(aid);
		if(A.get() != null) {
			List<Movie>temp =  A.get().getMoviesActed();
			return temp;
		}
		return null;
	}
	
	// Post when actor has movies is not working in itself
	@PostMapping("/api/actor")
	public Actor createActor(@RequestBody Actor actor) {
		return actorDao.processActor(actor);
	}
	
	@PutMapping("/api/actor/{aid}")
	public Actor updateUser(
			@PathVariable("aid") int aid,
			@RequestBody Actor actor) {
		return actorDao.updateActor(actor , aid);
	}
	
	
	@DeleteMapping("/api/actor/{aid}")
	public void deleteActor(@PathVariable("aid") int id) {
		actorDao.deleteActor(id);
	}
	
	
}
