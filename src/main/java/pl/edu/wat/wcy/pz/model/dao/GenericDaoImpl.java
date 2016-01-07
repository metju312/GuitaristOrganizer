package pl.edu.wat.wcy.pz.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDaoImpl<T> implements GenericDao<T> {

	Class<T> type;
    private Class<T> persistentClass;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
	EntityManager em;

    @SuppressWarnings("unchecked")
	public GenericDaoImpl(Class<T> type) {
		super();
		em = emf.createEntityManager();
		this.type = type;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T create(T t) {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(t);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
		return t;
	}

	@Override
	public void delete(Object id) {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.remove(em.getReference(type, id));
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Override
	public T retrieve(Object id) {
		T tmp;
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			tmp = em.find(type, id);
			transaction.commit();
			return tmp;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
		return null;
	}

	@Override
	public T update(T t) {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(t);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
		return t;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return em.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
    }
}

