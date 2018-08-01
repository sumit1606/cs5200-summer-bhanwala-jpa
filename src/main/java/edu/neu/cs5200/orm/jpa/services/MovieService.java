package edu.neu.cs5200.orm.jpa.services;

import java.util.ArrayList;
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
public class MovieService {

	@Autowired
	ActorDao actorDao;
	
	@Autowired
	MovieDao movieDao;
	
	@GetMapping("/api/movie")
	public List<Movie> findAllMovie() {
		List<Movie> movies = (List<Movie>) movieDao.findAllMovies();
		return movies;
	}
	
	@GetMapping("/api/movie/{mid}")
	public Movie findMovieById(@PathVariable("mid") int mid) {
		Movie m = movieDao.findMovieById(mid).get();
		List<Actor> thisMovieAct = m.getMovieActor();
		List<Actor> expandedAct = new ArrayList<Actor>();
		for(Actor a: thisMovieAct) {
			Actor newActor = new Actor();
			newActor.setId(a.getId());
			newActor.setFirstName(a.getFirstName());
			newActor.setLastName(a.getLastName());
			newActor.setOSCARNOMINATIONS(a.getOSCARNOMINATIONS());
			newActor.setDtype(a.getDtype());
			expandedAct.add(newActor);
		}
		m.setMovieActor(expandedAct);
		return m;
	}
	
	
	@GetMapping("/api/movie/{mid}/actor")
	public List<Actor> findMovieActorById(@PathVariable("mid") int mid) {
		Movie m = movieDao.findMovieById(mid).get();
		List<Actor> thisMovieAct = m.getMovieActor();
		return thisMovieAct;
	}
	
	@DeleteMapping("/api/movie/{mid}")
	public void deleteMovie(@PathVariable("mid") int mid) {
		movieDao.deleteMovieById(mid);
	}
	
	
	@PostMapping("/api/movie")
	public Movie createMovie(@RequestBody Movie m) {
		Movie movie = m;
		return movieDao.processMovie(movie);
	}
	
	
	@PutMapping("/api/movie/{mid}")
	public Movie updateMovie(
			@PathVariable("mid") int mid,
			@RequestBody Movie movie) {
		return movieDao.updateMovie(movie , mid);
	}
	
	
}
