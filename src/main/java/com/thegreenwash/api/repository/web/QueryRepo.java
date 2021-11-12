package com.thegreenwash.api.repository.web;

import com.thegreenwash.api.model.web.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QueryRepo extends MongoRepository<Query, String> {
    List<Query> findAllByNewQuery(boolean newQuery);
}
