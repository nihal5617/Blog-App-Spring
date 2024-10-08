package com.springboot.blogapp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.blogapp.service.CommentService;
import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.exception.BlogAPIException;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.model.Comment;
import com.springboot.blogapp.model.Post;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}

		commentRepository.delete(comment);
	}

	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

}
