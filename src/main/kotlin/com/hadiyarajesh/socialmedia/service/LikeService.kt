package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.LikeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository
) {
    fun likePost(userId: Long, postId: Long): Boolean {
        likeRepository.likePost(userId, postId)
        return true
    }

    fun unlikePost(userId: Long, postId: Long): Boolean {
        likeRepository.unlikePost(userId, postId)
        return true
    }

    fun getPostLikers(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return likeRepository.getPostLikers(postId, pageable)
    }

    fun getTotalPostLikers(postId: Long): Int {
        return likeRepository.getTotalPostLikers(postId)
    }
}