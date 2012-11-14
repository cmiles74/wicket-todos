package com.windsorsolutions.todos.service;

import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService {

    public String getHello() {
	return("Howdy!");
    }
}
