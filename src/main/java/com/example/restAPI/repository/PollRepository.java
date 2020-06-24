package com.example.restAPI.repository;

import com.example.restAPI.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {

}