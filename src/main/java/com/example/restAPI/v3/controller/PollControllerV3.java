package com.example.restAPI.v3.controller;

import com.example.restAPI.domain.Poll;
import com.example.restAPI.exception.ResourceNotFoundException;
import com.example.restAPI.repository.PollRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping({"/v3/", "/oauth2/v3/"})
public class PollControllerV3 {

    @Inject
    private PollRepository pollRepository;

    @RequestMapping(value="/polls", method= RequestMethod.GET)
    public ResponseEntity<?> getAllPolls(Pageable pageable) {
        Page<Poll> allPolls = pollRepository.findAll(pageable);
        if (pageable.getPageNumber() > allPolls.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value="/polls", method=RequestMethod.POST)
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {

        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll p = pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll with id " + pollId + " not found"));
        return new ResponseEntity<> (p, HttpStatus.OK);
    }

    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll p = pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Poll p = pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll with id " + pollId + " not found"));
    }
}

