package com.purely.repositories;


import com.purely.modals.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String userId);
    boolean existsByUserId(String userId);

}
