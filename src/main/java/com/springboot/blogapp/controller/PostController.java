package com.springboot.blogapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.dto.PostResponse;
import com.springboot.blogapp.service.PostService;
import com.springboot.blogapp.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@Operation(summary = "Create Post REST API", description = "Create Post REST API is used to save post into database")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@SecurityRequirement(name = "Bear Authentication")
	// create Blog post rest API
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@Operation(summary = "Get All Posts REST API", description = "Get All Posts REST API is used to fetch all the posts from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	// get all posts rest API
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	@Operation(summary = "Get Post By Id REST API", description = "Get Post By Id REST API is used to get single post from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	// get post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@Operation(summary = "update Post REST API", description = "Update Post REST API is used to update a particular post in the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@SecurityRequirement(name = "Bear Authentication")
	// update post by id rest API
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id) {

		PostDto postResponse = postService.updatePost(postDto, id);

		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@Operation(summary = "Delete Post REST API", description = "Delete Post REST API is used to delete a particular post from the database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@SecurityRequirement(name = "Bear Authentication")
	// delete post rest API
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {

		postService.deletePostById(id);

		return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
	}

	// Build Get Posts by Category REST API
	// http://localhost:8080/api/posts/category/3
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
		List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}
}