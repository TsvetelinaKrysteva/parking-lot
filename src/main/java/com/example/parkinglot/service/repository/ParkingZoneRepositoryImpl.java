package com.example.parkinglot.service.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingZone;

public class ParkingZoneRepositoryImpl implements ParkingZoneRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ParkingZone> findByFilter(ParkingZoneFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingZone> criteria = criteriaBuilder.createQuery(ParkingZone.class);
        Root<ParkingZone> root = criteria.from(ParkingZone.class);
        List<Predicate> predicates = new ArrayList<>();
        Fetch<ParkingZone, Parking> parkingFetch = root.fetch("parking");
        if(filter.getParkingName() != null){
            Join<ParkingZone, Parking> parkingJoin = root.join("parking");
            predicates.add(criteriaBuilder.like(parkingJoin.get("name"), filter.getParkingName()));
        }
        if(filter.getName() != null){
            predicates.add(criteriaBuilder.like(root.get("name"), filter.getName() + "%"));
        }
        criteria.orderBy(criteriaBuilder.asc(root.get("name")));
        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }

        TypedQuery<ParkingZone> result = entityManager.createQuery(criteria);


        return result.getResultList();
    }
}
