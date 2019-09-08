package com.rharris.robot;

import com.rharris.robot.domain.DefinedTasks;
import com.rharris.robot.domain.Robot;
import com.rharris.robot.domain.RobotType;
import com.rharris.robot.domain.TaskView;
import com.rharris.robot.repository.DefinedTasksRepository;
import com.rharris.robot.repository.RobotTypeRepository;
import com.rharris.robot.scheduler.RunnableRobotTask;
import com.rharris.robot.scheduler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
public class BotApp implements CommandLineRunner {

    @Autowired
    RobotTypeRepository robotTypeRepository;

    @Autowired
    DefinedTasksRepository definedTasksRepository;

    @Autowired
    TaskService taskService;

    public static void main(String[] args) {

        SpringApplication.run(BotApp.class);

    }

    /*
        The user cant decide whether he wants GUI or command line
        They have an option to decide
     */
    @Override
    public void run(String... args) throws Exception {

        String GUI = "GUI";
        String COMMON_LINE = "Command Line";
        System.out.println("Welcome to BOT-O-MAT");
        System.out.println("Would you like to use GUI or Command line");

        Scanner userChoice = new Scanner(System.in);

        if (userChoice.nextLine().equalsIgnoreCase(GUI)) {

            System.out.println("Please navigate to the following url -->" + " localhost:8080/home");
            System.out.println("Finish all tasks for a surprise!");

        } else {

            createBots();
        }
    }

    private void createBots() throws InterruptedException {

        TaskService taskService = new TaskService();
        List<RobotType> robotTypes = robotTypeRepository.findAll();
        List<DefinedTasks> definedTasksList = definedTasksRepository.findAll();

        //List of scheduled tasks to be ran by executor
        List<RunnableRobotTask> scheduledTasks = new ArrayList<>();

        System.out.println("How many robots would you like to create?");
        Scanner amountOfRobots = new Scanner(System.in);
        int robotCount = amountOfRobots.nextInt();

        System.out.println("Now select bot type(s) from the list below (Comma separated): ");

        robotTypes.forEach(robotType -> {

            System.out.println(robotType.getName());

        });

        Scanner userDefinedTypes = new Scanner(System.in);
        String types = userDefinedTypes.nextLine();
        String[] selectedTypes = types.split(",");

        //Types and robots selected must match
        if (selectedTypes.length == robotCount) {

            System.out.println("Now enter the names for your bot(s) (Comma separated): ");
            Scanner botNames = new Scanner(System.in);
            String name = botNames.nextLine();
            String[] names = name.split(",");

            //Names and robots selected must match
            if (names.length == robotCount) {

                List<Future<TaskView>> robotsList = null;
                ExecutorService executorService = (ExecutorService) Executors.newFixedThreadPool(robotCount + 2);

                for (int i = 0; i < robotCount; i++) {

                    Robot robot = new Robot();
                    int type = 0;
                    Collections.shuffle(definedTasksList); //randomize list of tasks to assigned robot

                    if(!selectedTypes[i].isEmpty()) {

                        RobotType robotType = robotTypeRepository.getRobotTypeByName(selectedTypes[i].toUpperCase().trim());

                        if(robotType == null) {

                            System.out.println("Invalid type selected!!!");
                            System.exit(0);

                        } else {

                            type = robotType.getId();
                        }

                    }

                    robot.setName(names[i]);
                    robot.setRobotTypeId(type);
                    robot.setTasks(definedTasksList.subList(0, 5)); // 5 tasks per robot

                    List<RunnableRobotTask> robotAssignedTasks = taskService.createTasks(robot);
                    scheduledTasks.addAll(robotAssignedTasks);
                }

                robotsList = executorService.invokeAll(scheduledTasks);

                for (Future<TaskView> future : robotsList) {

                    try {
                        System.out.println(future.get().getResult());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                }


            } else {

                startAgain();
            }

        } else {

            startAgain();
        }

    }

    private void startAgain() throws InterruptedException {

        System.out.println("Amount of types and names must match amount of robots! Would you like to start again?");
        Scanner startAgain = new Scanner(System.in);

        if (startAgain.nextLine().equalsIgnoreCase("Yes")) {

            this.createBots();

        } else {

            System.out.println("Goodbye!");
            System.exit(0);
        }
    }

}
