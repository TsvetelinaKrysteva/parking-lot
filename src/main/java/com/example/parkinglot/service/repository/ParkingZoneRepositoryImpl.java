package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingZone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ParkingZoneRepositoryImpl implements ParkingZoneRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ParkingZone> findByFilter(ParkingZoneFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingZone> criteria = criteriaBuilder.createQuery(ParkingZone.class);
        Root<ParkingZone> root = criteria.from(ParkingZone.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getParkingName() != null){
            Join<ParkingZone, Parking> parkingJoin = root.join("parking");
            predicates.add(criteriaBuilder.equal(parkingJoin.get("name"), filter.getParkingName()));
        }


        criteria.orderBy(criteriaBuilder.asc(root.get("name")));
        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }

        TypedQuery<ParkingZone> result = entityManager.createQuery(criteria);


        return result.getResultList();
    }
}
