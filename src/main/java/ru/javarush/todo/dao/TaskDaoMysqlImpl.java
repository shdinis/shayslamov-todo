package ru.javarush.todo.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.entity.Task;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class TaskDaoMysqlImpl implements TaskDao{
    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        Query<Task> query = getSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Task> getById(int id) {
        Query<Task> query = getSession().createQuery("select t from Task t where t.id = :ID", Task.class);
        query.setParameter("ID", id);
        return Optional.ofNullable(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
