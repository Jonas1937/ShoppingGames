package br.com.supera.game.store.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.supera.game.store.models.Cart;
import br.com.supera.game.store.models.Product;
import br.com.supera.game.store.repositories.CartRepository;

@Service
public class CartService {

    private final BigDecimal PRECO_FRETE_GRATIS = BigDecimal.valueOf(250);

    @Autowired
    private CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public Cart createCart(Product prod) {

        List<Product> prods = new ArrayList<Product>();
        prods.add(prod);
        Cart cart = new Cart();
        cart.setProducts(prods);
        cart = attCartInfos(cart, prods);
        return repository.save(cart);
    }

    public Cart checkout(long id) {
        return repository.getById(id);
    }

    public List<Cart> findAll() {
        return repository.findAll();
    }

    public Cart addProduct(long id, Product product) {
        Cart cartToAdd = repository.getById(id);
        List<Product> prodsAtuais = cartToAdd.getProducts();
        prodsAtuais.add(product);
        cartToAdd.setProducts(prodsAtuais);
        cartToAdd = attCartInfos(cartToAdd, prodsAtuais);
        return cartToAdd;
    }

    public Cart removeProduct(long id, Product product) {
        Cart cart = repository.getById(id);
        cart.getProducts().remove(product);
        cart.setSubTotal((cart.getSubTotal().subtract(product.price)));
        if (cart.getSubTotal().compareTo(PRECO_FRETE_GRATIS) == 1) {
            cart.setFrete(BigDecimal.valueOf(0));
        } else {
            cart.setFrete(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(cart.getProducts().size())));
        }
        cart.setTotal(cart.getSubTotal().add(cart.getFrete()));
        return repository.save(cart);
    }

    private Cart attCartInfos(Cart cart, List<Product> products) {
        for (Product product : products) {
            cart.setSubTotal(cart.getSubTotal().add(product.price));
        }
        if (cart.getSubTotal().compareTo(precoFreteGratis) == 1) {
            cart.setFrete(BigDecimal.valueOf(0));
        } else {
            cart.setFrete(BigDecimal.valueOf(products.size() * 10, 00));
        }
        cart.setTotal(cart.getSubTotal().add(cart.getFrete()));
        return cart;
    }

}
