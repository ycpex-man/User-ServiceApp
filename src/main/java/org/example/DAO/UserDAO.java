package org.example.DAO;

import jakarta.persistence.TransactionRequiredException;
import org.example.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collections;
import java.util.List;

public class UserDAO {
    private SessionFactory sessionFactory;
    private Transaction transaction = null;
    private Logger logger = LoggerFactory.getLogger(UserDAO.class);


    public UserDAO(){
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void add(User user) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.info("Добавление пользователя: {}", user.getEmail());
        } catch (ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка при добавлении пользователя: Email уже существует.");
        } catch (TransactionRequiredException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка: Транзакция не была начата.");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка Hibernate: {}", e.getMessage(), e);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка при добавлении пользователя: {}", e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public List<User> getAllUsers(){
        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> users = query.list();
            logger.info("Вывод {} пользователей: ", users.size());
            return users;
        } catch (HibernateException e){
            logger.error("Ошибка при получении пользователей: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public User get(int userId){
        try(Session session = sessionFactory.openSession()){
            User findedUser = session.find(User.class, userId);
            if (findedUser != null) logger.info("Найден пользователь с Id {}", findedUser.getId());
            else logger.warn("Пользователь с Id {} не найден", userId);
            return findedUser;
        } catch (HibernateException e){
            logger.error("Ошибка при получении пользователя: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage(), e);
        }
        return null;
    }

    public void update(int userId, String newEmail){
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            User user = session.find(User.class, userId);
            if (user != null){
                user.setEmail(newEmail);
                session.merge(user);
                transaction.commit();
            } else {
                logger.warn("Пользователь с Id {} не найден", userId);
                transaction.rollback();
            }
            logger.info("Email пользователя с Id {} изменён.", user.getId());
        } catch (ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Этот email уже существует.");
        } catch (TransactionRequiredException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка: Транзакция не была начата.");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка Hibernate: {}", e.getMessage(), e);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка: {}", e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public void delete(int userId){
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            User user = session.find(User.class, userId);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                logger.info("Пользователь с Id {} удалён.", user.getId());
            } else {
                logger.warn("Пользователь с Id {} не найден.", userId);
                transaction.rollback();
            }
        } catch (TransactionRequiredException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка: Транзакция не была начата.");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка Hibernate: {}", e.getMessage(), e);
        } catch (Exception e) {
            if (transaction != null)  transaction.rollback();
            logger.error("Ошибка: {}", e.getMessage(), e);
        }
    }

}
