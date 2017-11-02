'use strict';

App.controller('ReferenceController', ['$scope', 'Reference', function ($scope, Reference) {
        var self = this;
        self.cost;
        self.averagecost;
        self.references = [];
        self.reference = {id: null, cost: '', station: null, fuel: null};

        self.getAllReferences = function () {
            Reference.getAll()
                    .then(
                            function (d) {
                                self.references = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching references');
                            }
                    );
        };

        self.getReference = function (id) {
            Reference.get(id)
                    .then(
                            function (d) {
                                self.reference = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching reference');
                            }
                    );
        };

        self.saveReference = function (reference) {
            Reference.save(reference)
                    .then(
                            self.getAllReferences(),
                            function (errResponse) {
                                console.error('Error while save reference');
                            }
                    );
        };

        self.updateReference = function (reference, id) {
            Reference.update(reference, id)
                    .then(
                            self.getAllReferences(),
                            function (errResponse) {
                                console.error('Error while update reference');
                            }
                    );
        };

        self.deleteAllReferences = function () {
            Reference.deleteAll()
                    .then(
                            self.getAllReferences(),
                            function (errResponse) {
                                console.error('Error while deleting references');
                            }
                    );
        };

        self.deleteReference = function (id) {
            Reference.deleteReference(id)
                    .then(
                            self.getAllReferences(),
                            function (errResponse) {
                                console.error('Error while deleting reference');
                            }
                    );
        };

        self.getCost = function (fuel_id, station_id) {
            Reference.getCost(fuel_id, station_id).then(
                    function (d) {
                        self.cost = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching cost');
                    }
            );
        };

        self.getAverageCost = function (fuel_id) {
            Reference.getAverageCost(fuel_id).then(
                    function (d) {
                        self.averagecost = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching average cost');
                    }
            );
        };

        self.getReferencesByFuel = function (id) {
            Reference.getReferencesByFuel(id)
                    .then(
                            function (d) {
                                self.references = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching references');
                            }
                    );
        };

        self.getAllReferences();
    }]);


