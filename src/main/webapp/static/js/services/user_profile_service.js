App.factory('Profile', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/profile')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching profile');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/profile/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting profile');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (profile) {
                return $http.post(SERVER_URL_JSON + "/profile/", profile)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting profile');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (profile, id) {
                return $http.put(SERVER_URL_JSON + "/profile/" + id, profile)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting profile');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteProfile: function (id) {
                return $http.delete(SERVER_URL_JSON + "/profile/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting profile');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/profile')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting profiles');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


