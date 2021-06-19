package br.com.supera.game.store.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.supera.game.store.models.Cart;
import br.com.supera.game.store.services.CartService;

@RestController
@RequestMapping("api/cart")
public class CartController {
    
    @Autowired
    CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public Cart checkCart(@PathVariable(value = "id") long id){
        return service.checkout(id);
    }

    @GetMapping
    public List<Cart> allCart(){
        return service.findAll();
    }
}
