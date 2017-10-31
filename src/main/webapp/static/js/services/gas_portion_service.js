App.factory('Portion', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/portion')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching portion');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/portion/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting portion');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (portion) {
                return $http.post(SERVER_URL_JSON + "/portion/", portion)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting portion');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (portion, id) {
                return $http.put(SERVER_URL_JSON + "/portion/" + id, portion)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting portion');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deletePortion: function (id) {
                return $http.delete(SERVER_URL_JSON + "/portion/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting portion');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAllPortions: function () {
                return $http.delete(SERVER_URL_JSON + '/portion')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting portions');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


