'use strict';

App.controller('HomeController', ['$scope', 'Fuel', 'Reference', 'Station',
    function ($scope, Fuel, Reference, Station) {
        var self = this;
        self.fuel = {id: null, name: null};
        self.station = {id: null, name: null};
        self.fuels = [];
        self.stations = [];
        self.table_content = [];

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

        self.getAllStations = function () {
            Station.getAll()
                    .then(
                            function (d) {
                                self.stations = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching stations');
                            }
                    );
        };

        self.getCost = function (fuel_id, station_id) {
            Reference.getCost()
                    .then(
                            function (d) {
                                return d;
                            },
                            function (errResponse) {
                                return '-';
                            }
                    );
        };

        self.createContent = function () {
            self.getAllFuels();
            self.getAllStations();
            for (var i; i < self.fuels.length; i++) {
                var column = [];
                for (var j; j < self.stations.length; j++) {
                    column.push(self.getCost(
                            fuels[i].id, stations[j].id));

                }
                self.table_content.push(column);
            }
        };
        self.createContent();
    }]);


