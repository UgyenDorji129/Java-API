package com.hus23.assignment.socialmediaplatform.repository;

import com.hus23.assignment.socialmediaplatform.entity.Post;
import com.hus23.assignment.socialmediaplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

      Optional<Post> findByPostId(Integer postId);
      List<Post> findByPostLocationIgnoreCaseContaining(String postLocation);
      void deleteByUser(User user);
      void deleteById(Integer pid);
      @Query("SELECT p FROM Post p WHERE p.user = :user ORDER BY p.lastChangedTime DESC")
      List<Post> findAllByUserOrderByLastChangedTime(@Param("user") User user);

}
