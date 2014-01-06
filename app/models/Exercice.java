package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class Exercice extends Model {

    @Required
	public String identifier;
    @Required
	public String maxTime;
    @Required
	public String maxMistakes;
    @Required
	public String description;
	public boolean done; 
	public String time;
	public String mistakes;
    public String createdBy;
    public Date creationDate;
    public boolean deleted;
    public boolean copied;
    @Required
	public String[] formulation;
    @Required
	public String[] solution;
    @ManyToMany
    public List<User> users;

	public Exercice(String identifier, String maxTime, String maxMistakes,
			String[] formulation, String[] solution, String description, String createdBy) {

		this.identifier = identifier;
		this.maxTime = maxTime;
		this.maxMistakes = maxMistakes;
		this.formulation = formulation;
		this.solution = solution;
		this.description = description;
		this.done = false;
		this.time = "null";
		this.mistakes = "null";
        this.createdBy = createdBy;
        this.deleted = false;
        this.creationDate = Calendar.getInstance().getTime();
        this.copied = false;
        this.users = new ArrayList<User>();
	}

}
