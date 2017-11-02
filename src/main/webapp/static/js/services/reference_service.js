App.factory('Reference', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/reference')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching reference');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/reference/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting reference');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (reference) {
                return $http.post(SERVER_URL_JSON + "/reference/", reference)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting reference');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (reference, id) {
                return $http.put(SERVER_URL_JSON + "/reference/" + id, reference)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting reference');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteReference: function (id) {
                return $http.delete(SERVER_URL_JSON + "/reference/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting reference');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/reference')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting references');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            getCost: function (fuel_id, station_id) {
                return $http.get(SERVER_URL_JSON + '/cost?' +
                        'fuel=' + fuel_id +
                        'station=' + station_id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting cost');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            getAverageCost: function (id) {
                return $http.get(SERVER_URL_JSON + '/averagecost/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting average cost');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            getReferencesByFuel: function (id) {
                return $http.get(SERVER_URL_JSON + '/referencebyfuel/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting references');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };

    }]);


