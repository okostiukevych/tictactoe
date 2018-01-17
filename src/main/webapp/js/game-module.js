var gameModule = angular.module('game-module', []);

gameModule.controller('newGameController', ['$rootScope', '$scope', '$http', '$location',

    function (rootScope, scope, http, location) {

        rootScope.gameId = null;
        scope.newGameData = null;

        scope.newGameOptions = {
            name: '',
            availableGameTypes: [
                {name: 'ONE_VS_ONE', value: 'One vs One'},
                {name: 'ONE_VS_COMPUTER', value: 'One vs Computer'}
            ],
            selectedBoardDimension: {name: 'ONE_VS_ONE', value: 'One vs One'}
        };

        scope.createNewGame = function () {

            var data = scope.newGameData;
            var params = JSON.stringify(data);

            http.post("/game/create", params, {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data, status, headers, config) {
                rootScope.gameId = data.id;
                location.path('/game/' + rootScope.gameId);
            }).error(function (data, status, headers, config) {
                location.path('/games/panel');
            });
        }

    }
]);

gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',

    function (rootScope, routeParams, scope, http) {

        scope.moveNumber = 0;
        scope.rows = [
            [
                {'id': '11', 'letter': '', 'class': 'box'},
                {'id': '12', 'letter': '', 'class': 'box'},
                {'id': '13', 'letter': '', 'class': 'box'}
            ],
            [
                {'id': '21', 'letter': '', 'class': 'box'},
                {'id': '22', 'letter': '', 'class': 'box'},
                {'id': '23', 'letter': '', 'class': 'box'}
            ],
            [
                {'id': '31', 'letter': '', 'class': 'box'},
                {'id': '32', 'letter': '', 'class': 'box'},
                {'id': '33', 'letter': '', 'class': 'box'}
            ]
        ];

        var gameStatus;
        getInitialData();

        function getInitialData() {

            http.get('/game/' + routeParams.id).success(function (data) {
                scope.gameProperties = data;
                gameStatus = scope.gameProperties.gameStatus;
                // resetMoveNumber();
                getMoveHistory();
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
        }
        //
        // function resetMoveNumber() {
        //
        // }
        function getMoveHistory() {
            scope.movesInGame = [];

            return  http.get('/move/list').success(function (data) {
                scope.movesInGame = data;
                scope.playerMoves = [];

                //paint the board with positions from the retrieved moves
                angular.forEach(scope.movesInGame, function(move) {
                    scope.rows[move.boardRowNumber - 1][move.boardColumnNumber - 1].letter = move.moveNumber % 2 !== 0 ? 'X' : 'O';
                });
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed to load moves in game"
            });
        }

        scope.markPlayerMove = function (column) {
            var number = ++scope.moveNumber;
            var boardRow = parseInt(column.id.charAt(0));
            var boardColumn = parseInt(column.id.charAt(1));
            var params = {'boardRowNumber': boardRow, 'boardColumnNumber': boardColumn, 'moveNumber': number};

            if (checkIfBoardCellAvailable(boardRow, boardColumn)) {
                http.post("/move/create", params, {
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function () {
                    getMoveHistory().success(function () {

                        var gameStatus = scope.movesInGame[scope.movesInGame.length - 1].game.status;
                        if (gameStatus === 'IN_PROGRESS') {
                            getNextMove();
                        } else {
                            alert(gameStatus)
                        }
                    });

                }).error(function (data, status, headers, config) {
                    scope.errorMessage = "Can't send the move"
                });
            }
        };

        function getNextMove() {

            scope.nextMoveData = []
            // COMPUTER IS A SECOND PLAYER

            if(scope.gameProperties.type === 'ONE_VS_COMPUTER') {
                var number = ++scope.moveNumber;
                var params = {'moveNumber': number};

                http.post("/move/autoCreate", params, {
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function (data, status, headers, config) {
                    scope.nextMoveData = data;
                    getMoveHistory().success(function () {
                        var gameStatus = scope.movesInGame[scope.movesInGame.length - 1].game.status;
                        if (gameStatus !== 'IN_PROGRESS') {
                            alert(gameStatus)
                        }
                    });
                }).error(function (data, status, headers, config) {
                    scope.errorMessage = "Can't send the move"
                });

                // SECOND PLAYER IS A REAL USER
            } else {
                console.log(' another player move');
            }
        }

        function checkIfBoardCellAvailable(boardRow, boardColumn) {

            for (var i = 0; i < scope.movesInGame.length; i++) {
                var move = scope.movesInGame[i];
                if (move.boardColumnNumber === boardColumn && move.boardRowNumber === boardRow) {
                    return false;
                }
            }
            return true;
        }

        angular.forEach(scope.rows, function (row) {
            row[0].letter = row[1].letter = row[2].letter = '';
            row[0].class = row[1].class = row[2].class = 'box';
        });
    }]);

