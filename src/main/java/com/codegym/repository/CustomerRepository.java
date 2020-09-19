package com.codegym.repository;

import com.codegym.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class CustomerRepository implements ICustomerRepository<Customer> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c",Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(long id) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.id = :id",Customer.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e ) {
            return null;
        }
    }

    @Override
    public void save(Customer model) {
        if (findById(model.getId()) == null) {
            em.persist(model);
        } else {
            em.merge(model);
        }
    }

    @Override
    public void remove(long id) {
        em.remove(findById(id));
    }
}
