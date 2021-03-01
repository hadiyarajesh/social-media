package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PostRepository : Neo4jRepository<Post, Long> {
    fun existsByPostId(postId: Long): Boolean

    @Query("MATCH pst=(u:User{userId:\$userId})-[cp:CREATED_POST]->(p:Post{postId:\$postId}) RETURN pst")
    fun getPost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    ): Post?

    @Query("MATCH (u:User{userId:\$userId})-[cp:CREATED_POST]->(p:Post{postId:\$postId}) SET p.caption = \$caption RETURN p")
    fun editPost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("caption") caption: String?
    ): Post?

    @Query("MATCH (u:User{userId:\$userId})-[cp:CREATED_POST]->(p:Post{postId:\$postId}) MATCH (p)-[h:HAS]->(comments) DETACH DELETE comments,p")
    fun deletePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long
    )

    @Query("MATCH p=(u:User{userId:\$userId})-[cp:CREATED_POST]->(posts) RETURN p ORDER BY posts.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllPostByUser(
        @Param("userId") userId: Long,
        pageable: Pageable
    ): Slice<Post>

    @Query("MATCH (u:User{userId:\$userId})-[cp:CREATED_POST]->(posts) RETURN count(posts)")
    fun getTotalPostsCountByUser(
        @Param("userId") userId: Long,
    ): Int
}