'use strict';

App.controller('ProfileController', ['$scope', 'Profile', function ($scope, Profile) {
        var self = this;
        self.profiles = [];
        self.profile = {id: null, type: ''};

        self.getAllProfiles = function () {
            Profile.getAll()
                    .then(
                            function (d) {
                                self.profiles = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching profiles');
                            }
                    );
        };

        self.getProfile = function (id) {
            Profile.get(id)
                    .then(
                            function (d) {
                                self.profile = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching profile');
                            }
                    );
        };

        self.saveProfile = function (profile) {
            Profile.save(profile)
                    .then(
                            self.getAllProfiles(),
                            function (errResponse) {
                                console.error('Error while save profile');
                            }
                    );
        };

        self.updateProfile = function (profile, id) {
            Profile.update(profile, id)
                    .then(
                            self.getAllProfiles(),
                            function (errResponse) {
                                console.error('Error while update profile');
                            }
                    );
        };

        self.deleteAllProfiles = function () {
            Profile.deleteAll()
                    .then(
                            self.getAllProfiles(),
                            function (errResponse) {
                                console.error('Error while deleting profiles');
                            }
                    );
        };

        self.deleteProfile = function (id) {
            Profile.deleteProfile(id)
                    .then(
                            self.getAllProfiles(),
                            function (errResponse) {
                                console.error('Error while deleting profile');
                            }
                    );
        };
        self.getAllProfiles();
    }]);


