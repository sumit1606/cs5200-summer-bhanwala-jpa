package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.repositories.DirectorRepository;

@Component
public class DirectorDao {
	@Autowired
	DirectorRepository directoryRepository;
	
	/*
	 * Create an Actor given to the Repository
	 */
	public Director createDirector(Director d) {
		if (this.findDirectorByFirstAndLast(d.getFirstName(), d.getLastName()) == null) {
			d.setDtype("Director");
			directoryRepository.save(d);
		}
		return (Director)this.findDirectorByFirstAndLast(d.getFirstName(), d.getLastName());
	}
	
	public List<Director> findAllDirector() {
		return (List<Director>) directoryRepository.findAll();
	}
	
	public Optional<Director> findDirectorById(int id) {
		return directoryRepository.findById(id);
	}
	
	public void deleteDirector(int id) {
		directoryRepository.deleteById(id);
	}
	
	public void deleteAllDirector() {
		directoryRepository.deleteAll();
	}
	
	public Director findFirstDirector() {
		List<Director> allDirectors = this.findAllDirector();
		if(allDirectors.size() != 0)
			return allDirectors.get(0);
		return null;
	}
	
	public void updateDirector(Director d) {
		// add the below code to the upate crud
		directoryRepository.save(d);
	}
	
	public Director findDirectorByFirstAndLast(String firstName , String lastName) {
		return directoryRepository.findDirectorByName(firstName, lastName);
	}
	
	public void test() {
		
		this.deleteAllDirector();
		
		Director d1 =  new Director();
		d1.setFirstName("Jimmy");
		d1.setLastName("Camaron");
		
	
		Director d2 =  new Director();
		d2.setFirstName("Steven");
		d2.setLastName("Spielberg");
		
		Director d3 =  new Director();
		d3.setFirstName("Ron");
		d3.setLastName("Howard");
		
		this.createDirector(d1);
		this.createDirector(d2);
		this.createDirector(d3);
		
		Director topDirector = this.findFirstDirector();
		System.out.println(topDirector.getFirstName() + " " + topDirector.getLastName());
		
		for (Director d : this.findAllDirector()) {
			System.out.println(d.getFirstName() + " " + d.getLastName());
		}
		
		Director wrong = this.findDirectorByFirstAndLast("Jimmy", "Camaron");
		wrong.setFirstName("James");
		wrong.setLastName("Cameron");
		this.updateDirector(wrong);
		
		Director toDelete = this.findDirectorByFirstAndLast("Ron", "Howard");
		this.deleteDirector(toDelete.getId());
		for (Director d : this.findAllDirector()) {
			System.out.println(d.getFirstName() + " " + d.getLastName());
		}
		
	}
	
	
}
