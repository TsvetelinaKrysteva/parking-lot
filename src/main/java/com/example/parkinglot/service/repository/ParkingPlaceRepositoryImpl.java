package com.example.parkinglot.service.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import org.apache.commons.lang3.StringUtils;

public class ParkingPlaceRepositoryImpl implements ParkingPLaceRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ParkingPlace> findByFilter(ParkingPlaceFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingPlace> criteria = criteriaBuilder.createQuery(ParkingPlace.class);
        Root<ParkingPlace> root = criteria.from(ParkingPlace.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getParkingZoneName() != null){
            Join<ParkingPlace, ParkingZone> parkingZoneJoin = root.join("parkingZone");
            predicates.add(criteriaBuilder.equal(parkingZoneJoin.get("number"), Integer.parseInt(filter.getNumber())));
        }

       if(StringUtils.isNoneBlank(filter.getNumber())){

        predicates.add(criteriaBuilder.like(root.get("name"),filter.getNumber() +"%" ));
           // TODO: 26.1.2023 г.  convert integer root value name to string
       }

        criteria.orderBy(criteriaBuilder.desc(root.get("id")));
        if (!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }


        TypedQuery<ParkingPlace> result = entityManager.createQuery(criteria);



        return result.getResultList();
    }
}
