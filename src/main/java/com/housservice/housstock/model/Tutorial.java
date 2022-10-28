package com.housservice.housstock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Tutorials")
public class Tutorial {
	
	  @Id
	  private long id;

	  private String title;

	  private String description;

	  private boolean published;

	  public Tutorial() {

	  }

	  public Tutorial(String title, String description, boolean published) {
	    this.title = title;
	    this.description = description;
	    this.published = published;
	  }

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getTitle() {
	    return title;
	  }

	  public void setTitle(String title) {
	    this.title = title;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public boolean isPublished() {
	    return published;
	  }

	  public void setPublished(boolean isPublished) {
	    this.published = isPublished;
	  }

	  @Override
	  public String toString() {
	    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	  }

}
