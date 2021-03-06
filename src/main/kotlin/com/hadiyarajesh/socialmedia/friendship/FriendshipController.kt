package com.hadiyarajesh.socialmedia.friendship

import com.hadiyarajesh.socialmedia.utils.createResponseMapFromSlice
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friendship")
class FriendshipController(
    private val friendshipService: FriendshipService
) {
    @PostMapping("/follow/{currentUserId}")
    fun followUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = friendshipService.followUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isFollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/approve/{currentUserId}")
    fun approveFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isApproved = friendshipService.approveFollowRequest(currentUserId, friendshipRequest.userId)
        val response = mapOf("isApproved" to isApproved)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/reject/{currentUserId}")
    fun rejectFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isRejected = friendshipService.rejectFollowRequest(currentUserId, friendshipRequest.userId)
        val response = mapOf("isRejected" to isRejected)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/cancel/{currentUserId}")
    fun cancelFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isCanceled = friendshipService.cancelFollowRequest(currentUserId, friendshipRequest.userId)
        val response = mapOf("isCanceled" to isCanceled)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/unfollow/{currentUserId}")
    fun unfollowUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = friendshipService.unfollowUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnfollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/block/{currentUserId}")
    fun blockUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isBlocked = friendshipService.blockUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isBlocked" to isBlocked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/unblock/{currentUserId}")
    fun unblockUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isUnblocked = friendshipService.unblockUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnblocked" to isUnblocked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("remove/{currentUserId}")
    fun removeUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isRemoved = friendshipService.removeUser(currentUserId, friendshipRequest.userId)
        val responseMap = mapOf("isRemoved" to isRemoved)
        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/pending/{currentUserId}")
    fun getPendingFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = friendshipService.getPendingFollowRequest(currentUserId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("/sent/{currentUserId}")
    fun getSentFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = friendshipService.getSentFollowRequest(currentUserId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("/followers/{currentUserId}")
    fun getUserFollowers(
        @PathVariable currentUserId: Long,
        @RequestParam userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = friendshipService.getUserFollowers(currentUserId, userId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("/following/{currentUserId}")
    fun getUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = friendshipService.getUserFollowing(currentUserId, userId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("/checkfollowing/{currentUserId}")
    fun checkUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Map<String, Boolean>> {
        val result = friendshipService.isUserFollowing(currentUserId, userId)
        val responseMap = mapOf("isFollowing" to result)
        return ResponseEntity.ok(responseMap)
    }
}