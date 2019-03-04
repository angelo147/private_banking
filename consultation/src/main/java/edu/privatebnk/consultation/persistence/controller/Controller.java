package edu.privatebnk.consultation.persistence.controller;

import edu.privatebnk.consultation.persistence.model.Customer;
import edu.privatebnk.consultation.persistence.model.InvestmentProfile;
import edu.privatebnk.consultation.persistence.model.UserEntity;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
public class Controller {
    @PersistenceContext(unitName = "PrivateBankingDS")
    private EntityManager em;
    private final static Logger log = Logger.getLogger(Controller.class);

    public Customer findById(int id) {
        log.infov("finding UserEntity instances for id {0}", id);
        Customer user;
        try {
            user = em.find(Customer.class, id);
        } catch (RuntimeException re) {
            log.error("find by Id failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", user);
        return user;
    }
    public UserEntity findByυId(int id) {
        log.infov("finding UserEntity instances for id {0}", id);
        UserEntity user;
        try {
            user = em.find(UserEntity.class, id);
        } catch (RuntimeException re) {
            log.error("find by Id failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", user);
        return user;
    }
    public UserEntity findActiveByUsername(String username) {
        log.infov("finding UserEntity instances ActiveByUsername for username {0}", username);
        UserEntity user;
        try {
            user = em.createNamedQuery("UserEntity.findActiveByUsername", UserEntity.class)
                    .setParameter("username", username)
                    .getResultList().stream().findFirst().orElse(null);
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", user);
        return user;
    }

    public List<Customer> findCustomersForCro(int id) {
        List<Customer> user;
        try {
            user = em.createNamedQuery("Customer.findAll", Customer.class)
                    .setParameter("croid", id)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", user);
        return user;
    }

    public List<InvestmentProfile> findProfilesforCust(int customerid) {
        List<InvestmentProfile> user;
        try {
            user = em.createNamedQuery("InvestmentProfile.findAll", InvestmentProfile.class)
                    .setParameter("customerid", customerid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", user);
        return user;
    }
}
