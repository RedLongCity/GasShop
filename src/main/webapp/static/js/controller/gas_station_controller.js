'use strict';

App.controller('StationController', ['$scope', 'Station', function ($scope, Station) {
        var self = this;
        self.stations = [];
        self.station = {id: null, name: null};

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

        self.getStation = function (id) {
            Station.get(id)
                    .then(
                            function (d) {
                                self.station = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching station');
                            }
                    );
        };

        self.saveStation = function (station) {
            Station.save(station)
                    .then(
                            self.getAllStations(),
                            function (errResponse) {
                                console.error('Error while save station');
                            }
                    );
        };

        self.updateStation = function (station, id) {
            Station.update(station, id)
                    .then(
                            self.getAllStations(),
                            function (errResponse) {
                                console.error('Error while update station');
                            }
                    );
        };

        self.deleteAllStations = function () {
            Station.deleteAll()
                    .then(
                            self.getAllStations(),
                            function (errResponse) {
                                console.error('Error while deleting stations');
                            }
                    );
        };

        self.deleteStation = function (id) {
            Station.deleteStation(id)
                    .then(
                            self.getAllStations(),
                            function (errResponse) {
                                console.error('Error while deleting station');
                            }
                    );
        };
        self.getAllStations();
    }]);


