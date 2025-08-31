package org.example.services;

import org.example.daos.BrandDao;
import org.example.exceptions.BrandNotFoundException;
import org.example.models.Brand;

import java.util.List;

public class BrandServiceImpl implements BrandService {

    private final BrandDao brandDao;

    public BrandServiceImpl(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    public Brand findById(Integer id) {
        Brand brand = brandDao.findById(id);
        if (brand == null) {
            throw new BrandNotFoundException("Brand with id " + id + " not found.");
        }
        return brand;
    }

    @Override
    public void save(Brand brand) {
        brandDao.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        if (brandDao.findById(brand.getId()) == null) {
            throw new BrandNotFoundException("Cannot update. Brand with id " + brand.getId() + " not found.");
        }
        return brandDao.update(brand);
    }

    @Override
    public void delete(Brand brand) {
        if (brandDao.findById(brand.getId()) == null) {
            throw new BrandNotFoundException("Cannot delete. Brand with id " + brand.getId() + " not found.");
        }
        brandDao.delete(brand);
    }

    @Override
    public List<Brand> findByCountry(String country) {
        return brandDao.findByCountry(country);
    }
}
