package com.todo;

import com.todo.core.SubTask;
import com.todo.core.Task;
import com.todo.db.TaskDao;
import com.todo.resources.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TodoApplication extends Application<TodoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodoApplication().run(args);
    }

    @Override
    public String getName() {
        return "Todo";
    }

    private final HibernateBundle<TodoConfiguration> hibernate =
            new HibernateBundle<TodoConfiguration>(Task.class, SubTask.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TodoConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final TodoConfiguration configuration,
                    final Environment environment) {
        final TaskDao taskDao = new TaskDao(hibernate.getSessionFactory());
        environment.jersey().register(new TaskResource(taskDao));
    }
}

/*
{
    "name": "vamsi",
    "description": "Mfadsjfge",
    "subtasks": [
        {
        "sequence": 1,
        "name": "MT2019131",
        "description": "vcjfd something... "
        },
        {
        "sequence": 2,
        "name": "vamsi ponnada",
        "description": "start with something something... and something else"
        }
    ]
}
*/