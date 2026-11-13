
import { useState, useEffect } from "react";
import API from "./api";
import "./App.css";

function TodoPage({ onLogout }) {

  const [title, setTitle] = useState("");
  const [todos, setTodos] = useState([]);

  // GET
  const getTodos = async () => {
    const response = await API.get("");
    setTodos([...response.data].reverse());
  };

  useEffect(() => {
    getTodos();
  }, []);

  // POST
  const addTodo = async () => {
    if (!title.trim()) return;

    await API.post("/create", {
      title,
      iscompleted: false
    });

    setTitle("");
    getTodos();
  };

  // PUT
  const updateTodo = async (todo) => {
    await API.put("", {
      ...todo,
      iscompleted: !todo.iscompleted
    });

    getTodos();
  };

  // DELETE
  const deleteTodo = async (id) => {
    await API.delete(`/${id}`);
    getTodos();
  };

  return (
    <div className="app">

      <nav className="top-navbar">
        <div className="nav-brand">
          <span className="brand-icon">✓</span>
          <span>TaskFlow</span>
        </div>

        <button className="logout-btn" onClick={onLogout}>
          <span>Logout</span>
          <span>↗</span>
        </button>
      </nav>

      <main className="container">

        <header className="page-header">
          <span className="eyebrow">YOUR PERSONAL WORKSPACE</span>
          <h1>My Todo List<span>.</span></h1>
          <p>Plan your day, focus on what matters, and get things done.</p>
        </header>

        <section className="form-card">
          <div className="section-title">
            <div>
              <h2>Create a task</h2>
              <p>What's something you want to accomplish?</p>
            </div>
            <span className="plus-icon">+</span>
          </div>

          <div className="task-input-group">
            <input
              type="text"
              placeholder="Enter your task title..."
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") addTodo();
              }}
            />

            <button className="add-btn" onClick={addTodo}>
              + Add Task
            </button>
          </div>
        </section>

        <section className="tasks-section">

          <div className="task-heading-row">
            <div>
              <h2>Your Tasks</h2>
              <p>Keep track of your progress.</p>
            </div>

            <span className="task-count">
              {todos.length} {todos.length === 1 ? "TASK" : "TASKS"}
            </span>
          </div>

          <div className="todo-list">

            {todos.length === 0 ? (
              <div className="empty">
                <div className="empty-icon">✦</div>
                <h3>No tasks yet</h3>
                <p>Add your first task and start being productive!</p>
              </div>
            ) : (
              todos.map((todo) => (
                <div className="todo-card" key={todo.id}>

                  <div className="todo-info">
                    <h3 className={todo.iscompleted ? "done" : ""}>
                      {todo.title}
                    </h3>

                    <span className={
                      todo.iscompleted ? "completed" : "pending"
                    }>
                      <span className="status-dot"></span>
                      {todo.iscompleted ? "Completed" : "Pending"}
                    </span>
                  </div>

                  <div className="actions">
                    <button
                      className="complete-btn"
                      onClick={() => updateTodo(todo)}
                    >
                      {todo.iscompleted ? "Undo" : "✓ Complete"}
                    </button>

                    <button
                      className="delete-btn"
                      onClick={() => deleteTodo(todo.id)}
                    >
                      Delete
                    </button>
                  </div>

                </div>
              ))
            )}

          </div>
        </section>

        <footer className="footer">
          Made to keep your day organized.
        </footer>

      </main>
    </div>
  );
}

export default TodoPage;
