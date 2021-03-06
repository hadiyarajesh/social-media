package com.hadiyarajesh.socialmedia.posts

import com.hadiyarajesh.socialmedia.exception.ActionNotAllowed
import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.users.UserRepository
import com.hadiyarajesh.socialmedia.comments.CommentService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentService: CommentService
) {
    fun createPost(userId: Long, postId: Long, mediaType: String, caption: String?): Post {
        val user = userRepository.findByUserId(userId)
            ?: throw ResourceNotFound("User $userId not found")

        return postRepository.save(
            Post(
                postId = postId,
                mediaType = mediaType,
                caption = caption,
                createdAt = Instant.now(),
                totalLikes = 0,
                totalComments = 0,
                user = user
            )
        )
    }

    fun getPost(userId: Long, postId: Long): Post {
        return postRepository.getPost(userId, postId)
            ?: throw ResourceNotFound("Post $postId not found for User $userId")
    }

    fun editPost(userId: Long, postId: Long, caption: String?): Post {
        if (!postRepository.isPostBelongsToUser(userId, postId)) {
            throw ActionNotAllowed("Post $postId does not belongs to User $userId")
        }
        return postRepository.editPost(userId, postId, caption)
            ?: throw ResourceNotFound("Either user $userId or Post $postId not found")
    }

    fun deletePost(userId: Long, postId: Long) {
        if (!postRepository.isPostBelongsToUser(userId, postId)) {
            throw ActionNotAllowed("Post $postId does not belongs to User $userId")
        }
        commentService.deleteAllCommentsByPost(postId)
        postRepository.deletePost(userId, postId)
    }

    fun getAllPostsByUser(userId: Long, page: Int, size: Int): Slice<Post> {
        val pageable = PageRequest.of(page, size)
        return postRepository.getAllPostByUser(userId, pageable)
    }

    fun getTotalPostCountByUser(userId: Long): Int {
        return postRepository.getTotalPostsCountByUser(userId)
    }

    fun deleteAllPostsByUser(userId: Long) {
        val totalPost = getTotalPostCountByUser(userId)
        if (totalPost > 0) {
            getAllPostsByUser(userId = userId, page = 0, size = totalPost).forEach { post ->
                commentService.deleteAllCommentsByPost(post.postId)
            }
            postRepository.deleteAllPostsByUser(userId)
        }
    }
}