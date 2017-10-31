'use strict';

App.controller('FuelController', ['$scope', 'Fuel', function ($scope, Fuel) {
        var self = this;
        self.fuels = [];
        self.fuel = {id: null, name: ''};

        self.getAllFuels = function () {
            Fuel.getAll()
                    .then(
                            function (d) {
                                self.fuels = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching fuels');
                            }
                    );
        };

        self.getFuel = function (id) {
            Fuel.get(id)
                    .then(
                            function (d) {
                                self.fuel = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching fuel');
                            }
                    );
        };

        self.saveFuel = function (fuel) {
            Fuel.save(fuel)
                    .then(
                            self.getAllFuels(),
                            function (errResponse) {
                                console.error('Error while save fuel');
                            }
                    );
        };

        self.updateFuel = function (fuel, id) {
            Fuel.update(fuel, id)
                    .then(
                            self.getAllFuels(),
                            function (errResponse) {
                                console.error('Error while update fuel');
                            }
                    );
        };

        self.deleteAllFuels = function () {
            Fuel.deleteAll()
                    .then(
                            self.getAllFuels(),
                            function (errResponse) {
                                console.error('Error while deleting fuels');
                            }
                    );
        };

        self.deleteFuel = function (id) {
            Fuel.deleteFuel(id)
                    .then(
                            self.getAllFuels(),
                            function (errResponse) {
                                console.error('Error while deleting fuel');
                            }
                    );
        };
        self.getAllFuels();
    }]);


