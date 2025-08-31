package org.example.daos;

import org.example.models.Brand;

import java.util.List;

public interface BrandDao extends CrudDao<Brand, Integer> {


    List<Brand> findByCountry(String country);
}
