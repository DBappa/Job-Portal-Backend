package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.Sequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService{

    private final MongoOperations mongoOperations;

    public SequenceGeneratorServiceImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Long getNextSequence(String key) {

        Query query = new Query(Criteria.where("id").is(key));
        Update update = new Update().inc("seq", 1);

        FindAndModifyOptions options = FindAndModifyOptions
                .options()
                .returnNew(true)
                .upsert(true);

        Sequence counter = mongoOperations.findAndModify(
                query,
                update,
                options,
                Sequence.class);

        return counter.getSeq();
    }
}
