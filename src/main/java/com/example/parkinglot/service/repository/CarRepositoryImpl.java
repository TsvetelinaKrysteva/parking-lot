package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
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

public class CarRepositoryImpl implements CarRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Car> findByFilter(CarFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();



        if(filter.getPlateNumber() != null) {
            predicates.add(criteriaBuilder.like(root.get("plateNumber"), filter.getPlateNumber() + "%"));
        }
        if(filter.getParkingZoneName() != null){
            Join<Car, ParkingPlace> placeJoin = root.join("parkingPlace");
            Join<ParkingPlace, ParkingZone> zoneJoin = placeJoin.join("parkingZone");
            predicates.add(criteriaBuilder.equal(zoneJoin.get("name") ,filter.getParkingZoneName()));

        }


        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }


        TypedQuery<Car> result = entityManager.createQuery(criteria);
        return result.getResultList();


    }
}
