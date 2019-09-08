(function () {

    'use strict';

    angular.module('bot.controller').controller('homeController', homeController);

    homeController.$inject = ['$scope', 'botService'];

    function homeController($scope, botService) {

        $scope.headerName = "Bot-O-Mat";
        $scope.executedTasks = [];
        $scope.bots = [];
        $scope.botTypes = [];

        getCurrentBots();
        getAmountOfRobotTypes();
        getDefinedTasks();

        function getCurrentBots() {

            botService.getAllBots()
                .then(function (response) {

                    $scope.bots = response.data;

                    if (response.data.length > 0) {

                        $scope.amountOfActiveBots = response.data.length;

                    } else {

                        $scope.amountOfActiveBots = 0;
                    }

                });

            return $scope.bots;

        }

        function getAmountOfRobotTypes() {

            botService.getAllBotTypes()
                .then(function (response) {

                    $scope.botTypes = response.data;

                    if (response.data.length > 0) {

                        $scope.amountOfRobotTypes = response.data.length;

                    } else {

                        $scope.amountOfRobotTypes = 0;
                    }


                })
        }

        function getDefinedTasks() {

            botService.getAllBotTasks()
                .then(function (response) {

                    $scope.tasks = response.data;
                    $scope.amountOfTasks = $scope.tasks.length;
                    shuffle($scope.tasks);

                });

            function shuffle(array) {

                for (let i = array.length - 1; i > 0; i--) {
                    const j = Math.floor(Math.random() * i);
                    const temp = array[i];
                    array[i] = array[j];
                    array[j] = temp
                }
            }

        }

        function createTasks(robot) {

            botService.createTask(robot)
                .then(function (response) {

                    console.log('Tasks created!');

                    $scope.tasksLoading = false;
                    $scope.completedTasks = response.data;

                    var completedTasks = [];

                    for(let i = 0; i < response.data.length; i++){
                        completedTasks.push(response.data[i].taskId);
                    }

                    botService.deleteTasks(completedTasks)
                        .then(function (response) {

                            getDefinedTasks();

                    });

                }, handleError);

            function handleError(response) {
                return response;
            }


        }

        $scope.add = function () {

            $scope.completedTasks = '';

            var bot = {
                id: 3,
                name: $scope.robotName,
                robotTypeId: parseInt($scope.selectedType, 10),
                tasks: $scope.tasks.slice(0, 5)
            };

            console.log(bot);

            botService.saveBot(bot)
                .then(function (response) {

                    $scope.tasksLoading = true;
                    getCurrentBots();
                    createTasks(bot);

                });
        };

        $scope.botChange = function () {

            getDefinedTasks();
        };

    }
})();

