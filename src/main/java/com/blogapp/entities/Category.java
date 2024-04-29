package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    
    

    public Category() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
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



	public List<Post> getPostList() {
		return postList;
	}



	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}



	@OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();
}
