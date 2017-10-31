'use strict';

App.controller('PortionController', ['$scope', 'Portion', function ($scope, Portion) {
        var self = this;
        self.portions = [];
        self.portion = {id: null, amount: '', station:null, fuel:null};

        self.getAllPortions = function () {
            Portion.getAll()
                    .then(
                            function (d) {
                                self.portions = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching portions');
                            }
                    );
        };

        self.getPortion = function (id) {
            Portion.get(id)
                    .then(
                            function (d) {
                                self.portion = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching portion');
                            }
                    );
        };

        self.savePortion = function (portion) {
            Portion.save(portion)
                    .then(
                            self.getAllPortions(),
                            function (errResponse) {
                                console.error('Error while save portion');
                            }
                    );
        };

        self.updatePortion = function (portion, id) {
            Portion.update(portion, id)
                    .then(
                            self.getAllPortions(),
                            function (errResponse) {
                                console.error('Error while update portion');
                            }
                    );
        };

        self.deleteAllPortions = function () {
            Portion.deleteAll()
                    .then(
                            self.getAllPortions(),
                            function (errResponse) {
                                console.error('Error while deleting portions');
                            }
                    );
        };

        self.deletePortion = function (id) {
            Portion.deletePortion(id)
                    .then(
                            self.getAllPortions(),
                            function (errResponse) {
                                console.error('Error while deleting portion');
                            }
                    );
        };
        self.getAllPortions();
    }]);


