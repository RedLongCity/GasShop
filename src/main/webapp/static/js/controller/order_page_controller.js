'use strict';

App.controller('OrderPageController', ['$scope', 'Fuel', 'Reference', 'Station',
    function ($scope, Fuel, Reference, Station) {
        var self = this;
        self.fuel = {id: null, name: null};
        self.station = {id: null, name: null};
        self.reference = {id: null, cost: null, fuel: null, station: null};
        self.fuels = [];
        self.average_costs = [];
        self.references = [];
        self.amount;
        self.rowReferenceIndex = -1;
        self.rowFuelIndex = -1;

        self.selectReferenceRow = function (index,reference) {
                self.rowReferenceIndex = index;
                self.openModal(reference);
                self.rowReferenceIndex = -1;
        }

        self.selectFuelRow = function (index, fuel) {
            if (index != self.rowFuelIndex) {
                self.fuel = fuel;
                self.rowFuelIndex = index;
                self.references = self.getReferencesByFuel(fuel.id);
            }
        }
        
        self.onFillForm = function(){
            
        }

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

        self.getAverageCost = function (fuel_id) {
            Reference.getAverageCost(fuel_id).then(
                    function (d) {
                        return d;
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

        self.createContent = function () {
            self.getAllFuels();
            for (var i; i < self.fuels.length; i++) {
                self.average_costs.push(self.getAverageCost(fuels[i].id));
            }
            self.rowFuelIndex = 0;
            self.fuel = self.fuels[0];
            self.references = self.getReferencesByFuel(self.fuel.id);
        };

        self.createContent();
    }]);


