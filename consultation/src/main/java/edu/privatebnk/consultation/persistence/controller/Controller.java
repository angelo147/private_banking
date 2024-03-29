package edu.privatebnk.consultation.persistence.controller;

import edu.privatebnk.consultation.persistence.model.*;
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

    public void create(Object o) {
        em.persist(o);
    }

    public void update(Object o) {
        em.merge(o);
    }

    public List<Charge> findCharges() {
        List<Charge> charge;
        try {
            charge = em.createNamedQuery("Charge.findAll", Charge.class)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by Charge failed", re);
            return null;
        }
        log.infov("Found {0} Charge relationships!", charge);
        return charge;
    }

    public InvestProposal findProposalById(int id) {
        log.infov("finding InvestProposal instances for id {0}", id);
        InvestProposal user;
        try {
            user = em.find(InvestProposal.class, id);
        } catch (RuntimeException re) {
            log.error("find by Id failed", re);
            return null;
        }
        log.infov("Found {0} InvestProposal relationships!", user);
        return user;
    }

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

    public List<ConsultRequest> findConsultRequestsBystatusCRO(int userid, boolean proccessed) {
        List<ConsultRequest> requests;
        try {
            requests = em.createNamedQuery("ConsultRequest.findAllByStatusCRO", ConsultRequest.class)
                    .setParameter("proccessed", proccessed)
                    .setParameter("userid", userid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", requests);
        return requests;
    }

    public List<ConsultRequest> findConsultRequestsCRO(int userid) {
        List<ConsultRequest> requests;
        try {
            requests = em.createNamedQuery("ConsultRequest.findAllCRO", ConsultRequest.class)
                    .setParameter("userid", userid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", requests);
        return requests;
    }

    public List<ConsultRequest> findConsultRequestsBystatusMA(int userid, boolean proccessed) {
        List<ConsultRequest> requests;
        try {
            requests = em.createNamedQuery("ConsultRequest.findAllByStatusMA", ConsultRequest.class)
                    .setParameter("proccessed", proccessed)
                    .setParameter("userid", userid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", requests);
        return requests;
    }

    public List<ConsultRequest> findConsultRequestsMA(int userid) {
        List<ConsultRequest> requests;
        try {
            requests = em.createNamedQuery("ConsultRequest.findAllMA", ConsultRequest.class)
                    .setParameter("userid", userid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", requests);
        return requests;
    }

    public List<ConsultReport> findConsultReportMA(int userid) {
        List<ConsultReport> reports;
        try {
            reports = em.createNamedQuery("ConsultReport.findAll", ConsultReport.class)
                    .setParameter("userid", userid)
                    .getResultList();
        } catch (RuntimeException re) {
            log.error("find by ActiveByUsername failed", re);
            return null;
        }
        log.infov("Found {0} UserEntity relationships!", reports);
        return reports;
    }

    public ConsultRequest findRequestById(int id) {
        log.infov("finding ConsultRequest instances for id {0}", id);
        ConsultRequest user;
        try {
            user = em.createNamedQuery("ConsultRequest.findById", ConsultRequest.class)
                    .setParameter("id", id)
                    .getResultList().stream().findFirst().orElse(null);
        } catch (RuntimeException re) {
            log.error("find by Id failed", re);
            return null;
        }
        log.infov("Found {0} ConsultRequest relationships!", user);
        return user;
    }
}
