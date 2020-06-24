package com.example.restAPI.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class VoteResult {
    private int totalVotes;
    private Collection<OptionCount> results;
}
