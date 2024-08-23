package org.okten.demo.repository;

import org.bson.types.ObjectId;
import org.okten.demo.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {
}
