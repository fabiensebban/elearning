package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Exercice extends Model {

	public String identifier;
	public String maxTime;
	public String maxMistakes;
	public String description;
	public boolean done; 
	public String time;
	public String mistakes;
    public String createdBy;

    //@Lob
	public String[] formulation;
	//@Lob
	public String[] solution;
    @ManyToMany
    public List<User> users;

	public Exercice(String identifier, String maxTime, String maxMistakes,
			String[] formulation, String[] solution, String description, String CreaatedBy) {

		this.identifier = identifier;
		this.maxTime = maxTime;
		this.maxMistakes = maxMistakes;
		this.formulation = formulation;
		this.solution = solution;
		this.description = description;
		this.done = false;
		this.time = "0";
		this.mistakes = "0";
        this.createdBy = createdBy;

	}

}
