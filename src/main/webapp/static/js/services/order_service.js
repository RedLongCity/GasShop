App.factory('Order', ['$http', '$q', 'UrlService', function ($http, $q, UrlService) {

        var SERVER_URL_JSON = UrlService.getServerUrlJson();

        return {

            getAll: function () {
                return $http.get(SERVER_URL_JSON + '/order')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching order');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            get: function (id) {
                return $http.get(SERVER_URL_JSON + "/order/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting order');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            save: function (order) {
                return $http.post(SERVER_URL_JSON + "/order/", order)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting order');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            update: function (order, id) {
                return $http.put(SERVER_URL_JSON + "/order/" + id, order)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while posting order');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteOrder: function (id) {
                return $http.delete(SERVER_URL_JSON + "/order/" + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting order');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            deleteAll: function () {
                return $http.delete(SERVER_URL_JSON + '/order')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting orders');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };

    }]);


