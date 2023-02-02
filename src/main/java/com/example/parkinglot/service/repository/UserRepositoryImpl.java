package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.UserFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.model.entity.User;

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

public class UserRepositoryImpl implements UserRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<User> findByFilter(UserFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getName() != null){
            predicates.add(root.get("name").in(filter.getName()));
        }
        if(filter.getCarPlate() != null){
            Join<User, Car> carJoin = root.join("car");
            predicates.add((criteriaBuilder.equal(carJoin.get("plateNumber") ,filter.getCarPlate())));
        }

        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true);
        }


        TypedQuery<User> result = entityManager.createQuery(criteria);
        return result.getResultList();
    }
}
