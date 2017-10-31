'use strict';

App.controller('ReferenceController', ['$scope', 'Reference', function ($scope, Reference) {
        var self = this;
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
        self.getAllReferences();
    }]);


