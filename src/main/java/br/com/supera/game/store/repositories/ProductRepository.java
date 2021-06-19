package br.com.supera.game.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.supera.game.store.models.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    @Query(nativeQuery = true, value = "SELECT * FROM Product Order By price")
    Iterable<Product> orderByPrice();
    
    @Query(nativeQuery = true, value = "SELECT * FROM Product Order By score")
    Iterable<Product> orderByScore();

    @Query(nativeQuery = true, value = "SELECT * FROM Product Order By name")
    Iterable<Product> orderByName();
}
