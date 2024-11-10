package com.fazalulabid.axel_machinetextcompose.domain.usecase

import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import com.fazalulabid.axel_machinetextcompose.domain.repository.TodoRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.BaseUseCase
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) : BaseUseCase<Unit, List<Todo>>() {

    override suspend fun execute(params: Unit): List<Todo> {
        val todos = todoRepository.getTodos()

        if (todos.isEmpty()) {
            throw NoSuchElementException("No todos available")
        }

        return todos
    }
}
