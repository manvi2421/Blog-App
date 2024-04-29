package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;
    private String imageName;
    private Date createdDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comment = new HashSet<>();
    
    

	public Post(int id, String title, String content, String imageName, Date createdDate, Category category, User user,
			Set<Comment> comment) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imageName = imageName;
		this.createdDate = createdDate;
		this.category = category;
		this.user = user;
		this.comment = comment;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
    
    
}