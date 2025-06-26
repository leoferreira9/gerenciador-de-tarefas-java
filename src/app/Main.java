package app;

import model.Priority;
import model.Task;
import service.TaskService;

import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static TaskService taskService = new TaskService();

    public static void main(String[] args) {

        int option;

        do{

            showMenu();
            option = readInt("\n--->: ");

            switch (option){
                case 1:
                    createTask();
                    break;
                case 2:
                    List<Task> tasks = taskService.listTasks();

                    if(tasks.isEmpty()){
                        System.out.println("\n❌ Nenhuma tarefa encontrada.");
                    } else {
                        tasks.forEach(System.out::println);
                    }
                    break;

                case 3:
                    if(taskService.listTasks().isEmpty()){
                        System.out.println("\n❌ Nenhuma tarefa encontrada.");
                    } else {
                        List<Task> priorityTasks = priorityFilter();

                        if(priorityTasks.isEmpty()){
                            System.out.println("\n❌ Nenhuma tarefa encontrada.");
                        } else {
                            priorityTasks.forEach(System.out::println);
                        }
                    }
                    break;

                case 4:
                    if(taskService.listTasks().isEmpty()){
                        System.out.println("\n❌ Nenhuma tarefa encontrada.");
                    } else {
                        dueDateFilter().forEach(System.out::println);
                    }
                    break;

                case 5:
                    setTaskAsDone();
                    break;

                case 6:
                    deleteTask();
                    break;

                case 7:
                    taskService.fullSummary();
                    break;

                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }

        } while (option != 8);
    }


    public static Priority choosePriority(){
        Priority priority = null;

        do{
            System.out.println(
                    "\nEscolha o nível de prioridade:" +
                            "\n1 - Baixa" +
                            "\n2 - Média" +
                            "\n3 - Alta"
            );

            int option = readInt("\n--->: ");

            switch (option){
                case 1 -> priority = Priority.BAIXA;
                case 2 -> priority = Priority.MEDIA;
                case 3 -> priority = Priority.ALTA;
                default -> System.out.println("\nOpção inválida!");
            }

        }while(priority == null);

        return priority;
    }

    public static void createTask(){

        String title;
        String description;
        LocalDate dueDate;
        int day;
        int month;
        int year;


        do{
            System.out.print("\nDigite o título da tarefa: ");
            title = sc.nextLine();
        }while(title.isEmpty());

        do{
            System.out.print("\nDigite a descrição da tarefa: ");
            description = sc.nextLine();
        }while(description.isEmpty());

        System.out.println("\nInforme uma dia/mes/ano para a tarefa.");

        day = readInt("Dia: ");
        month = readInt("Mês: ");
        year = readInt("Ano: ");

        dueDate = LocalDate.of(year, month, day);

        if(dueDate.isBefore(LocalDate.now())){
            System.out.println("\n⚠️ A data informada já passou. Deseja continuar?");
            int option = readInt("\n1 - Sim\n2 - Não\n--->: ");

            if(option == 2){
                return;
            }
        }

        Priority priority = choosePriority();
        taskService.addTask(new Task(title, description, dueDate, priority));
    }

    public static List<Task> priorityFilter(){
        Priority priority = choosePriority();
        return taskService.searchByPriority(priority);
    }

    public static List<Task> dueDateFilter(){
        LocalDate today = LocalDate.now();
        return taskService.listTasks().stream().filter(t -> t.getDueDate().isBefore(today)).toList();
    }

    public static void setTaskAsDone(){
        int id;

        do {
            id = readInt("\nInforme o ID da task: ");
        } while (id == 0);

        Task task = taskService.searchById(id);

        if(task == null){
            System.out.println("\n❌ Nenhuma tarefa encontrada.");
        } else {
            taskService.setAsDone(task);
        }
    }

    public static void deleteTask(){
        int id;

        do {
            id = readInt("\nInforme o ID da task: ");
        } while (id == 0);

        Task task = taskService.searchById(id);

        if(task == null){
            System.out.println("\n❌ Nenhuma tarefa encontrada.");
        } else {
            taskService.deleteTask(task);
        }
    }

    public static int readInt(String prompt){
        int number = 0;
        boolean valid = false;

        while(!valid){
            System.out.print(prompt);
            try {
                number = Integer.parseInt(sc.nextLine());
                valid = true;
            }catch (NumberFormatException e){
                System.out.println("\n❌ Entrada inválida. Digite um número inteiro.");
            }
        }
        return number;
    }


    public static void showMenu(){
        System.out.print(
                "\nEscolha uma das opções abaixo:" +
                        "\n1 - Criar nova tarefa" +
                        "\n2 - Listar todas as tarefas" +
                        "\n3 - Filtrar por prioridade" +
                        "\n4 - Filtrar por tarefas vencidas" +
                        "\n5 - Marcar tarefa como concluída" +
                        "\n6 - Excluir tarefa" +
                        "\n7 - Ver resumo geral" +
                        "\n8 - Sair"
        );
    }
}