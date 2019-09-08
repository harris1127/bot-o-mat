(function () {

    'use strict';

    angular.module('bot.service').factory('botService', botService);

    function botService($http) {

        var service = {};

        service.getAllBotTypes = function() {
            var url = '/getAllTypes';
            return $http.get(url);
        };
        service.getAllBots = function() {
            return $http.get('/getAllRobots');
        };
        service.saveBot = function(bot) {

            return $http.post('/saveRobot', bot)
                .then(function (response) {

                    console.log('Save success!');
                    return response;

                }, handleError);

            function handleError(response) {
                return response;
            }

        };
        service.getAllBotTasks = function() {
            var url = '/getAllTasks';
            return $http.get(url);
        };
        service.createTask = function(robot) {

            return $http.post('/createTask', robot)
                .then(function (response) {
                    return response;
                }, handleError);

            function handleError(response) {
                return response;
            }

        };
        service.deleteTasks = function(taskId) {
            var url = '/deleteTasks';
            return $http.post(url, taskId);
        };
        return service;

    }

})();




