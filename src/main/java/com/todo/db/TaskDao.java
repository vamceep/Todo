package com.todo.db;

import com.todo.core.Task;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TaskDao extends AbstractDAO<Task> {
    public TaskDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Task findById(int id) {
        return get(id);
    }

    public List<Task> findAll() throws HibernateException {
        return list((Query<Task>)namedQuery("com.todo.core.Task.findAll"));
    }

    public int  deleteById(int id) throws HibernateException{
        deleteSubTasksofId(id);
        return namedQuery("com.todo.core.Task.deleteById").setParameter("id", id).executeUpdate();
    }

    public Task save(Task task) throws HibernateException{
        return persist(task);
    }

    public void deleteSubTasksofId(int id) throws HibernateException{
        namedQuery("com.todo.core.SubTask.delete").setParameter("id", id).executeUpdate();
    }
}
