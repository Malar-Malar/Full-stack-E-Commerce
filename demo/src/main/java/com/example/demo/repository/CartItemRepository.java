package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);
    void deleteByCart(Cart cart);
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM CartItem c WHERE c.cart.user.id = :userId")
////    void deleteAllByUserId(@Param("userId") Long userId);
//
//    void deleteAllByCartUserId(@Param("userId") Long userId);



}
