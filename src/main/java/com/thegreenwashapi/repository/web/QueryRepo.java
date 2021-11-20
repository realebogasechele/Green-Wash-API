package com.thegreenwashapi.repository.web;

import com.thegreenwashapi.model.web.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QueryRepo extends MongoRepository<Query, String> {
    List<Query> findAllByNewQuery(boolean newQuery);

    Query findByCellNoAndNewQuery(String cellNo, boolean newQuery);
}
