package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;
import edu.neu.cs5200.orm.jpa.repositories.ActorRepository;
import edu.neu.cs5200.orm.jpa.repositories.DirectorRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieLibraryRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieRepository;

@Component
public class MovieDao {
	@Autowired
	MovieRepository movieRepository;	
	
	@Autowired
	ActorRepository actorrepo;
	
	@Autowired
	DirectorRepository directorrepo;
	
	@Autowired
	ActorDao actorDao;
	
	@Autowired
	DirectorDao directorDao;
	
	@Autowired
	MovieLibraryDao movieLibraryDao;
	
	@Autowired
	MovieLibraryRepository movielibraryRepo;
	
	public void deleteAllMovie() {
		List<Movie> movies = (List<Movie>) movieRepository.findAll();
		for(Movie m: movies) {
			List<Actor> moviesActor = m.getMovieActor();
			for(Actor actor :moviesActor) {
				List<Movie> actedMovies = actor.getMoviesActed();
				Movie toBeRemoved = null;
				for(Movie movieTemp : actedMovies) {
					if(movieTemp.getId() == m.getId()) {
						toBeRemoved = movieTemp;
					}
				}
				actedMovies.remove(toBeRemoved);
				actor.setMoviesActed(actedMovies);
				actorrepo.save(actor);
			}
			
			List<Director> directors = m.getMovieDirector();
			for(Director d :directors) {
				List<Movie> moviesDirected = d.getMoviesDirected();
				Movie toBeRemoved = null;
				for(Movie movieTemp:moviesDirected) {
					if(movieTemp.getId() == m.getId()) {
						toBeRemoved = movieTemp;
					}
				}
				moviesDirected.remove(toBeRemoved);
				d.setMoviesDirected(moviesDirected);
				directorrepo.save(d);
			}
		}
		movieRepository.deleteAll();
	}
	
	public Movie createMovie(Movie m) {
		if (movieRepository.findMovieByTitle(m.getTitle()) == null ) {
			return movieRepository.save(m);
		}
		return movieRepository.findMovieByTitle(m.getTitle()); 
	}
	
	public void deleteMovieById(int id) {
		Movie m = movieRepository.findById(id).get();
			List<Actor> moviesActor = m.getMovieActor();
			for(Actor actor :moviesActor) {
				List<Movie> actedMovies = actor.getMoviesActed();
				Movie toBeRemoved = null;
				for(Movie movieTemp : actedMovies) {
					if(movieTemp.getId() == m.getId()) {
						toBeRemoved = movieTemp;
					}
				}
				actedMovies.remove(toBeRemoved);
				actor.setMoviesActed(actedMovies);
				actorrepo.save(actor);
			}
			
			List<Director> directors = m.getMovieDirector();
			for(Director d :directors) {
				List<Movie> moviesDirected = d.getMoviesDirected();
				Movie toBeRemoved = null;
				for(Movie movieTemp:moviesDirected) {
					if(movieTemp.getId() == m.getId()) {
						toBeRemoved = movieTemp;
					}
				}
				moviesDirected.remove(toBeRemoved);
				d.setMoviesDirected(moviesDirected);
				directorrepo.save(d);
		}
		movieRepository.deleteById(id);	
	}
	
	public Iterable<Movie> findAllMovies() {
		List<Movie> temp = (List<Movie>) movieRepository.findAll();
		return temp;
	}
	
	public Optional<Movie> findMovieById(int mid) {
		Optional<Movie> m = movieRepository.findById(mid);
		return m;
	}
	
	public void addActorToMovie(Actor a, Movie m) {
		Actor actor =  actorDao.findActorById(a.getId()).get();
		Movie movie =  movieRepository.findById(m.getId()).get();
		actor.getMoviesActed().add(movie);
		movie.getMovieActor().add(actor);
		movieRepository.save(movie);
		actorrepo.save(actor);
    }
	
	public void addDirectorToMovie(Director d, Movie m) {
		Director director = directorDao.findDirectorById(d.getId()).get();
		Movie movie =  movieRepository.findById(m.getId()).get();
		director.getMoviesDirected().add(movie);
		movie.getMovieDirector().add(director);
		movieRepository.save(movie);
		directorrepo.save(director);
	}
	
	
	public Movie processMovie(Movie m) {
		Movie m1 = new Movie(m.getTitle());
		m1 = this.createMovie(m1);
		if(m.getMovieActor() !=null) {
			for(Actor a: m.getMovieActor()) {
				Actor a1 = new Actor();
				a1.setFirstName(a.getFirstName());
				a1.setLastName(a.getLastName());
				a1 = actorDao.createActor(a1);
				addActorToMovie(a1, m1);
			}
		}
		if(m.getMovieDirector() != null) {
			for(Director d: m.getMovieDirector()) {
				Director d1 = new Director();
				d1.setFirstName(d.getFirstName());
				d1.setLastName(d.getLastName());
				d1 = directorDao.createDirector(d1);
				addDirectorToMovie(d1, m1);
			}
		}
		if(m.getLibrary() != null) {
			MovieLibrary temp  = m.getLibrary();
			MovieLibrary created = movieLibraryDao.createMovieLibraryByName(temp.getName());
			m1.setLibrary(created);
			movieRepository.save(m1);
		}
		return this.findMovieById(m1.getId()).get();
	}
	
	public Movie updateMovie(Movie m, int mid) {
		Movie m1 = this.findMovieById(mid).get();
		if(m1 != null) {
			m1.setTitle(m.getTitle());
			MovieLibrary temp = m.getLibrary();
			if(temp != null) {
				MovieLibrary created = movieLibraryDao.createMovieLibraryByName(temp.getName());
				m1.setLibrary(created);
			} else {
				m1.setLibrary(null);
			}
		}
		movieRepository.save(m1);				
		return this.findMovieById(mid).get();
	}
	
	
	public void test() {
		
		this.deleteAllMovie();
		
		Movie m1 = new Movie();
		m1.setTitle("Blade Runner");
		this.createMovie(m1);
	
		Actor a1 = new Actor();
		a1.setFirstName("Harrison");
		a1.setLastName("Ford");
		a1 = actorDao.createActor(a1);
		addActorToMovie(a1, m1);
		
		Actor a2 = new Actor();
		a2.setFirstName("Rutger");
		a2.setLastName("Hauer");
		a2 = actorDao.createActor(a2);
		addActorToMovie(a2, m1);		
	
		Director d1 = new Director();
		d1.setFirstName("Ridley");
		d1.setLastName("Scott");
		d1 = directorDao.createDirector(d1);
		addDirectorToMovie(d1, m1);
			
		
		Movie m2 = new Movie();
		m2.setTitle("Raiders of The Lost Ark");
		this.createMovie(m2);
		
		
		Actor a3 = new Actor();
		a3.setFirstName("Harrison");
		a3.setLastName("Ford");
		a3 = actorDao.createActor(a3);
		addActorToMovie(a3, m2);
		

		Actor a4 = new Actor();
		a4.setFirstName("Karen");
		a4.setLastName("Allen");
		a4 = actorDao.createActor(a4);
		addActorToMovie(a4, m2);
	
			
		Director d2 = new Director();
		d2.setFirstName("Steven");
		d2.setLastName("Spielberg");
		d2 = directorDao.createDirector(d2);
		addDirectorToMovie(d2, m2);		
		
		
		Movie m3 = new Movie();
		m3.setTitle("Close Encounters of the Third Kind");
		this.createMovie(m3);
		
		
		Actor a5 = new Actor();
		a5.setFirstName("Richard");
		a5.setLastName("Dreyfus");
		a5 = actorDao.createActor(a5);
		addActorToMovie(a5, m3);
		

		Actor a6 = new Actor();
		a6.setFirstName("Melinda");
		a6.setLastName("Dillon");
		a6 = actorDao.createActor(a6);
		addActorToMovie(a6, m3);
	
			
		Director d3 = new Director();
		d3.setFirstName("Steven");
		d3.setLastName("Spielberg");
		d3 = directorDao.createDirector(d3);
		addDirectorToMovie(d3, m3);
		
		
		List<Movie> movies = (List<Movie>) this.findAllMovies();
        for (Movie movie:movies) {
            System.out.println(movie.getTitle());
            for (Actor actor : movie.getMovieActor()) {
                System.out.println(actor.getFirstName()+" "+actor.getLastName());
            }
            for (Director director:movie.getMovieDirector()) {
                System.out.println(director.getFirstName()+" "+director.getLastName());
            }
        }
        
        Actor actor = actorDao.findActorByFirstAndLast("Harrison", "Ford");
        System.out.println(actor.getFirstName()+" "+actor.getLastName());
        for(Movie movie:actor.getMoviesActed()){
            System.out.println(movie.getTitle());
        }
        
        Director direc = directorDao.findDirectorByFirstAndLast("Steven", "Spielberg");
        System.out.println(direc.getFirstName()+" "+direc.getLastName());
        for(Movie movie:direc.getMoviesDirected()){
            System.out.println(movie.getTitle());
        }
        
        
	}
}
