package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.repositories.ActorRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieRepository;

@Component
public class ActorDao {
	@Autowired
	ActorRepository actorRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	MovieDao moviedao;
	/*
	 * Create an Actor given to the Repository
	 */
	public Actor createActor(Actor a) {
		if (this.findActorByFirstAndLast(a.getFirstName(), a.getLastName()) == null) {
			a.setDtype("Actor");
			actorRepository.save(a);
		}	
		return (Actor)this.findActorByFirstAndLast(a.getFirstName(), a.getLastName());
	}
	
	public Actor processActor(Actor a) {
		Actor createdAct = new Actor();
		createdAct.setFirstName(a.getFirstName());
		createdAct.setLastName(a.getLastName());
		createdAct.setOSCARNOMINATIONS(a.getOSCARNOMINATIONS());
		createdAct.setMoviesActed(new ArrayList<Movie>());
		createdAct = this.createActor(createdAct);
		for(Movie m: createdAct.getMoviesActed()) {
			Movie createdMovie = moviedao.createMovie(m); 
			if(createdMovie.getMovieActor() == null) {
				createdMovie.setMovieActor(new ArrayList<Actor>());
			}
			moviedao.addActorToMovie(createdAct, createdMovie);
		}
		return createdAct;
	}
	
	public List<Actor> findAllActor() {
		return (List<Actor>) actorRepository.findAll();
	}
	
	public Optional<Actor> findActorById(int id) {
		return actorRepository.findById(id);
	}
	
	public void deleteActor(int id) {
		actorRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAllActor() {
		actorRepository.deleteAll();
	}
	
	public Actor findFirstActor() {
		List<Actor> allActors = this.findAllActor();
		if(allActors.size() != 0)
			return allActors.get(0);
		return null;
	}
	
	public Actor updateActor(Actor a, int aid) {
		Actor returned = actorRepository.findById(aid).get();
		if(returned != null) {
			returned.setFirstName(a.getFirstName());
			returned.setLastName(a.getLastName());
			returned.setOSCARNOMINATIONS(a.getOSCARNOMINATIONS());
			return actorRepository.save(returned);
		}
		return null;
	}
	
	public Actor findActorByFirstAndLast(String firstName , String lastName) {
		return actorRepository.findActorByName(firstName, lastName);
	}
	
	public boolean isActorPresent(Actor a1) {
		// TODO Auto-generated method stub
		if (this.findActorByFirstAndLast(a1.getFirstName(), a1.getLastName()) !=null){
			return true;
		}
		return false;
	}
	
	public void test() {
		
		// Todo delete are not cascade
		this.deleteAllActor();
		
		Actor a1 =  new Actor();
		a1.setFirstName("Sigorney");
		a1.setLastName("Weaver");
		
		Actor a2 =  new Actor();
		a2.setFirstName("Dan");
		a2.setLastName("Creg");
		
		Actor a3 = new Actor();
		a3.setFirstName("Jim");
		a3.setLastName("Carrey");
		
		this.createActor(a1);
		this.createActor(a2);
		this.createActor(a3);

		
		Actor topActor = this.findFirstActor();
		System.out.println(topActor.getFirstName() + " " + topActor.getLastName());
		
		for (Actor a : this.findAllActor()) {
			System.out.println(a.getFirstName() + " " + a.getLastName());
		}
		
		Actor wrong = this.findActorByFirstAndLast("Dan", "Creg");
		wrong.setFirstName("Daniel");
		wrong.setLastName("Craig");
		this.updateActor(wrong,wrong.getId());
		Actor toDelete = this.findActorByFirstAndLast("Jim", "Carrey");
		this.deleteActor(toDelete.getId());
		for (Actor a : this.findAllActor()) {
			System.out.println(a.getFirstName() + " " + a.getLastName());
		}
		
	}
	
}
