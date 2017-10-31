'use strict';

App.controller('OrderController', ['$scope', 'Order', function ($scope, Order) {
        var self = this;
        self.orders = [];
        self.order = {id: null, date: '', status: '',
            portions: null, user: null};

        self.getAllOrders = function () {
            Order.getAll()
                    .then(
                            function (d) {
                                self.orders = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching orders');
                            }
                    );
        };

        self.getOrder = function (id) {
            Order.get(id)
                    .then(
                            function (d) {
                                self.order = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching order');
                            }
                    );
        };

        self.saveOrder = function (order) {
            Order.save(order)
                    .then(
                            self.getAllOrders(),
                            function (errResponse) {
                                console.error('Error while save order');
                            }
                    );
        };

        self.updateOrder = function (order, id) {
            Order.update(order, id)
                    .then(
                            self.getAllOrders(),
                            function (errResponse) {
                                console.error('Error while update order');
                            }
                    );
        };

        self.deleteAllOrders = function () {
            Order.deleteAll()
                    .then(
                            self.getAllOrders(),
                            function (errResponse) {
                                console.error('Error while deleting orders');
                            }
                    );
        };

        self.deleteOrder = function (id) {
            Order.deleteOrder(id)
                    .then(
                            self.getAllOrders(),
                            function (errResponse) {
                                console.error('Error while deleting order');
                            }
                    );
        };
        self.getAllOrders();
    }]);


