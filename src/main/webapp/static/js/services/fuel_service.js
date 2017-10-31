App.factory('Fuel', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/fuel')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching fuel');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/fuel/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting fuel');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (fuel) {
                return $http.post(SERVER_URL_JSON + "/fuel/", fuel)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting fuel');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (fuel, id) {
                return $http.put(SERVER_URL_JSON + "/fuel/" + id, fuel)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting fuel');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteFuel: function (id) {
                return $http.delete(SERVER_URL_JSON + "/fuel/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting fuel');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/fuel')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting fuels');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


