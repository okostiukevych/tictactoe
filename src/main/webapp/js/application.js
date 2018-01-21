var ticTacToe = angular.module('ticTacToe', ['ngRoute','game-module']);

ticTacToe.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/game/:id', {
            templateUrl: 'templates/game-board.html',
            controller: 'gameController'
        }).
        when('/games/panel', {
            templateUrl: 'templates/games-panel.html',
            controller: 'newGameController'
        }).
        otherwise({
            redirectTo: '/games/panel'
        });
}]);
