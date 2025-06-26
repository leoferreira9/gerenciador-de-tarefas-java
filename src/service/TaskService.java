package service;

import model.Priority;
import model.Task;
import model.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskService {

    private static int nextId = 1;
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task){
        task.setId(nextId++);
        task.setStatus(TaskStatus.PENDENTE);
        tasks.add(task);
        System.out.println("\n✅ Tarefa criada com sucesso! ID: (" + task.getId() + ")");
    }

    public List<Task> listTasks(){
        return tasks;
    }

    public List<Task> searchByPriority(Priority priority){
        Predicate<Task> priorityFilter = t -> t.getPriority().equals(priority);
        return tasks.stream().filter(priorityFilter).toList();
    }

    public Task searchById(int id){
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public void setAsDone(Task task){
        task.setStatus(TaskStatus.CONCLUIDA);
        System.out.println("\nO status da tarefa " + task.getTitle().toUpperCase() + " foi alterado para: Concluída!");
    }

    public void deleteTask(Task task){
        tasks.remove(task);
        System.out.println("\n✅ Tarefa " + task.getTitle().toUpperCase() + " removida com sucesso!");
    }

    public void fullSummary(){
        if(tasks.isEmpty()){
            System.out.println("\n❌ Não há tarefas cadastradas.");
        } else {
            long total = tasks.size();
            long pending = tasks.stream().filter(t -> t.getStatus().equals(TaskStatus.PENDENTE)).count();
            long completed = tasks.stream().filter(t -> t.getStatus().equals(TaskStatus.CONCLUIDA)).count();
            long overdue = tasks.stream().filter(t -> t.getDueDate().isBefore(LocalDate.now()) && t.getStatus().equals(TaskStatus.PENDENTE)).count();

            System.out.println("\n📊 RESUMO GERAL:");
            System.out.println("Total de tarefas: " + total);
            System.out.println("Concluídas: " + completed);
            System.out.println("Pendentes: " + pending);
            System.out.println("Vencidas: " + overdue);
        }
    }
}
