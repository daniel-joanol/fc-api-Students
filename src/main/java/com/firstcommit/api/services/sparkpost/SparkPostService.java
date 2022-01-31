package com.firstcommit.api.services.sparkpost;

import com.sparkpost.exception.SparkPostException;
import org.springframework.http.ResponseEntity;

public interface SparkPostService {

    ResponseEntity<?> sendMessage(String email) throws SparkPostException;
}
