package model.dao;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller dpt);
    void update(Seller dpt);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
