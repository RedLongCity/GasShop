App.factory('User', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/user')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching user');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/user/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting user');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (user) {
                return $http.post(SERVER_URL_JSON + "/user/", user)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting user');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (user, id) {
                return $http.put(SERVER_URL_JSON + "/user/" + id, user)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting user');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteUser: function (id) {
                return $http.delete(SERVER_URL_JSON + "/user/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting user');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/user')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting users');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


