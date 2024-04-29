package com.blogapp.payloads;

import com.blogapp.entities.Category;
import com.blogapp.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private int id;
    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    private String content;

    private String imageName;
    private Date date;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comment = new HashSet<>();
	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PostDto(int id,
			@NotNull(message = "Title cannot be null") @NotEmpty(message = "Title cannot be empty") String title,
			@NotNull(message = "Content cannot be null") @NotEmpty(message = "Content cannot be empty") String content,
			String imageName, Date date, CategoryDto category, UserDto user, Set<CommentDto> comment) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imageName = imageName;
		this.date = date;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public Set<CommentDto> getComment() {
		return comment;
	}
	public void setComment(Set<CommentDto> comment) {
		this.comment = comment;
	}
    
    
}
