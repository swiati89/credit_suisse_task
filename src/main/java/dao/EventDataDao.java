package dao;

import model.EventData;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EventDataDao implements SimpleGenericDao<EventData> {

    private Session currentSession;

    private Transaction currentTransaction;

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.configure("hibernate.cfg.xml").build());
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    @Override
    public void saveDataToDb(EventData object) throws HibernateException {
        getCurrentSession().save(object);
    }

    @Override
    public EventData getDataById(Long id){
        try{
            return getCurrentSession().get(EventData.class, id);
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public List<EventData> getAll(){
        Session session = getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<EventData> criteriaQuery = criteriaBuilder.createQuery(EventData.class);
            Root<EventData> rootEntry = criteriaQuery.from(EventData.class);
            CriteriaQuery<EventData> all = criteriaQuery.select(rootEntry);
            TypedQuery<EventData> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } finally {
            session.close();
        }
    }
}
