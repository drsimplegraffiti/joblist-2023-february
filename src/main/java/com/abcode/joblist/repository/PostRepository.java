package com.abcode.joblist.repository;


import com.abcode.joblist.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{'profile' : ?0}") // ?0 is the first parameter and it means that the profile is the first parameter
    List<Post> findByProfile(@Param("profile") String profile);
}
