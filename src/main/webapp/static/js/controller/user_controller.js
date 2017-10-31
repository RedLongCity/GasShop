'use strict';

App.controller('UserController', ['$scope', 'User', function ($scope, User) {
        var self = this;
        self.users = [];
        self.user = {id: null, firstName: '', lastName: '',
            email: '', tel: '', address: '', password: '',
            profile: null};

        self.getAllUsers = function () {
            User.getAll()
                    .then(
                            function (d) {
                                self.users = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching users');
                            }
                    );
        };

        self.getUser = function (id) {
            User.get(id)
                    .then(
                            function (d) {
                                self.user = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching user');
                            }
                    );
        };

        self.saveUser = function (user) {
            User.save(user)
                    .then(
                            self.getAllUsers(),
                            function (errResponse) {
                                console.error('Error while save user');
                            }
                    );
        };

        self.updateUser = function (user, id) {
            User.update(user, id)
                    .then(
                            self.getAllUsers(),
                            function (errResponse) {
                                console.error('Error while update user');
                            }
                    );
        };

        self.deleteAllUsers = function () {
            User.deleteAll()
                    .then(
                            self.getAllUsers(),
                            function (errResponse) {
                                console.error('Error while deleting users');
                            }
                    );
        };

        self.deleteUser = function (id) {
            User.deleteUser(id)
                    .then(
                            self.getAllUsers(),
                            function (errResponse) {
                                console.error('Error while deleting user');
                            }
                    );
        };
        self.getAllUsers();
    }]);


