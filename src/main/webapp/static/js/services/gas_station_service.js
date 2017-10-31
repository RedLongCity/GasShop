App.factory('Gas_Station', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/station')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching station');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/station/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting station');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (station) {
                return $http.post(SERVER_URL_JSON + "/station/", station)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting station');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (station, id) {
                return $http.put(SERVER_URL_JSON + "/station/" + id, station)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting station');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteGas_Station: function (id) {
                return $http.delete(SERVER_URL_JSON + "/station/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting station');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/station')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting stations');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


