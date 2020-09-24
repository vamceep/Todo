package com.todo.resources;

import com.google.gson.Gson;
import com.todo.core.SubTask;
import com.todo.core.Task;
import com.todo.db.TaskDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    private final TaskDao taskDao;
    public TaskResource(TaskDao dao) {
        taskDao = dao;
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response todos() {
        try {
            List<Task> ls = taskDao.findAll();
            return Response.ok(ls, MediaType.APPLICATION_JSON).build();
        }
        catch(Exception exception) {
            return Response.serverError().entity(exception.toString()).build();
        }
    }

    @GET
    @Path("{id}")
    @UnitOfWork
    public Response todos(@PathParam("id") int id) {
        System.out.println("entered vamsi id = "+ id);
        Task task = taskDao.findById(id);
        if(task == null)
            return Response.notAcceptable(null).status(404,"Task with id "+id+"not found.").build();

        return Response.ok(task, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String responseBody) {
        Gson gson = new Gson();
        Task task = gson.fromJson(responseBody, Task.class);
        if(task.getName() == null || task.getName().length() == 0) {
            return Response.notAcceptable(null).entity("Name of task field is empty or null.").build();
        }
        for(SubTask t : task.getSubtasks()) {
            t.setParent(task);
            if(t.getName() == null || t.getName().length() == 0) {
                return Response.notAcceptable(null).entity("Name of subtast field is empty or null.").build();
            }
        }
        taskDao.save(task);
        return Response.ok(task, MediaType.APPLICATION_JSON).status(201).build();
    }

    @PUT
    @UnitOfWork
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, String responseBody) {
        Gson gson = new Gson();
        Task task = gson.fromJson(responseBody, Task.class);
        task.setId(id);
        if(task.getName() == null || task.getName().length() == 0) {
            return Response.notAcceptable(null).entity("Name of task field is empty or null.").build();
        }
        for(SubTask t : task.getSubtasks()) {
            if(t.getName() == null || t.getName().length() == 0)
                return Response.notAcceptable(null).entity("Name of subtast field is empty or null.").build();
            t.setParent(task);
        }
        taskDao.deleteSubTasksofId(id);
        try {
            taskDao.save(task);
        }
        catch (Exception exception) {
            return Response.notAcceptable(null).status(404, "Id "+id+" not found").build();
        }

        return Response.ok(task, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response delete(@PathParam("id") int id) {
        int cnt = taskDao.deleteById(id);
        if(cnt == 0)
            return Response.notAcceptable(null).status(404, "Id "+id+" not found.").build();
        return Response.ok("Deleted id "+id+".", MediaType.TEXT_HTML).build();
    }
}
