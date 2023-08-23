package com.example.edoc.dao;

import com.example.edoc.models.Specialization;

import java.util.List;

public interface SpecializationDao {

    public Boolean save(Specialization specialization);

    public List<Specialization> findAll();

    public boolean delete(Long id);

    public Specialization findById(Long id);

}
