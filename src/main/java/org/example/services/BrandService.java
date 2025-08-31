package org.example.services;

import org.example.models.Brand;

import java.util.List;

public interface BrandService extends CrudService<Brand, Integer> {
    List<Brand> findByCountry(String country);
}
