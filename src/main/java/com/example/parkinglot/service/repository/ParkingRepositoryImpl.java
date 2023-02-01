package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ParkingRepositoryImpl implements ParkingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Parking> findByFilter(ParkingFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Parking> criteria = criteriaBuilder.createQuery(Parking.class);
        Root<Parking> root = criteria.from(Parking.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getName() != null) {
            predicates.add(root.get("name").in(filter.getName()));
        }
        if(filter.getCity() != null){
            predicates.add(criteriaBuilder.equal(root.get("city"), filter.getCity()));
        }

        if(filter.getParkingZoneName() != null) {
            Join<Parking, ParkingZone> zoneJoin = root.join("zones");
            predicates.add(criteriaBuilder.equal(zoneJoin.get("name") ,filter.getParkingZoneName()));
        }
        if(filter.getStreet() != null){
            predicates.add(criteriaBuilder.equal(root.get("street"), filter.getStreet()));
        }
        if(filter.getZipCode() != null){
            predicates.add(criteriaBuilder.like(root.get("zipCode"), "%" + filter.getZipCode() + "%"));
        }


        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }


        TypedQuery<Parking> result = entityManager.createQuery(criteria);

        return result.getResultList();
    }
}
