package com.windsorsolutions.todos;

import java.util.List;

public interface ToDoDao {

    public ToDo getToDo(Long id);

    public List getToDos();
}
