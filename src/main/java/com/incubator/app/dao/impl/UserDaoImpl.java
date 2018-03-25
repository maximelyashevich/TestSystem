package com.incubator.app.dao.impl;

import com.incubator.app.dao.UserDao;
import com.incubator.app.dao.util.HibernateUtil;
import com.incubator.app.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final static String DELETE_USER = "update User u set u.isDeleted = 1 where u.id=:id";
    private final static String FIND_BY_LOGIN = "from User u where login =:login and isDeleted=0";
    private final static String FIND_ALL = " from User u where u.isDeleted =0";

    @Override
    public void insert(User entity) {            //throws ConstraintViolationException e
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);                      //? persist()
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

    @Override
    public void update(User entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(entity);                     //? update()
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

    @Override
    public void delete(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(DELETE_USER);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

    @Override
    public User findById(long id) {
        User user = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        user = (User)session.get(User.class, id);
        session.getTransaction().commit();
        HibernateUtil.shutdown();
        return user;
    }

    @Override
    public User findByLogin(String login) {
        User user = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(FIND_BY_LOGIN);            //select all fields, how only 3
        query.setParameter("login", login);
        user = (User) query.uniqueResult();
        session.getTransaction().commit();
        HibernateUtil.shutdown();
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(FIND_ALL);
        users = query.list();
        session.getTransaction().commit();
        HibernateUtil.shutdown();
        return users;
    }

    @Override
    public void deleteSeveralUsers(long[] ids) {
        for (long id: ids) {
            delete(id);
        }
    }
}
