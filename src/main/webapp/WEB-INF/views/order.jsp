<%-- 
    Document   : order
    Created on : 02.11.2017, 17:04:03
    Author     : redlongcity
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <meta name="description" content="HomePage">

        <meta name="author" content="redlongcity">

        <title>Тестовое АЗС</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

        <!-- Icons -->
        <link href="static/css/font-awesome.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="static/css/style.css" rel="stylesheet">
    </head>
    <body ng-app="myApp">
        <main class="col-xs-12 col-sm-8 offset-sm-4 col-lg-9 offset-lg-3 col-xl-10 offset-xl-2 pt-3 pl-4">
            <header class="page-header row justify-center">
                <div class="col-md-6 col-lg-8" >
                    <h1 class="float-left text-center text-md-left">Test Gas Station</h1>
                </div>
                <div class="dropdown user-dropdown col-md-6 col-lg-4 text-center text-md-right"><a class="btn btn-stripped dropdown-toggle" href="https://example.com" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="static/images/profile-pic.jpg" alt="profile photo" class="circle float-left profile-photo" width="50" height="auto">

                        <div class="username mt-1">
                            <h4 class="mb-1">Admin</h4>

                            <h6 class="text-muted">Super Admin</h6>
                        </div>
                    </a>

                    <div class="dropdown-menu dropdown-menu-right" style="margin-right: 1.5rem;" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="#"><em class="fa fa-user-circle mr-1"></em> View Profile</a>
                        <a class="dropdown-item" href="#"><em class="fa fa-sliders mr-1"></em> Preferences</a>
                        <a class="dropdown-item" href="#"><em class="fa fa-power-off mr-1"></em> Logout</a></div>
                </div>
            </header>

            <section class="row">
                <div class="col-sm-12 col-xs-12 col-md-3 col-lg-4 schedule-table">
                    <div class="card text-center">
                        <h2 class="card-title">Средняя цена</h2>
                        <div class="table-responsive" ng-controller="OrderController as ctrl">
                            <table class="table text-center table-striped">
                                <thead>
                                    <tr>

                                        <th>Топливо</th>

                                        <th>Цена</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="f in ctrl.fuels" ng-class="'bg-primary':rowFuelIndex == $index" 
                                        ng-click="ctrl.selectFuelRow($index, f)">

                                        <td><span ng-bind="f"></span></td>

                                        <td><span ng-bind="ctrl.average_costs[$index]"></span></td>

                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12 col-xs-12 col-md-7 col-lg-8 schedule-table">
                    <div class="card text-center">
                        <h2 class="card-title">Цены на АЗС</h2>
                        <div class="table-responsive" ng-controller="OrderController as ctrl">
                            <table class="table text-center table-striped">
                                <thead>
                                    <tr>

                                        <th>Цена</th>

                                        <th>АЗС</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="r in ctrl.references" ng-class="'bg-primary'rowReferenceIndex == $index" 
                                        ng-click="ctrl.selectReferenceRow($index, r)">

                                        <td><span ng-bind="r.cost"></span></td>
                                        <td><span ng-bind="r.station.name"></span></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>

        </main>

        <!-- Modal -->
        <div class="modal fade" id="order_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Добавить в заказ:</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" ng-controller="OrderController as ctrl">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-8">
                                    АЗС:
                                    <span ng-bind=" ctrl.reference.station.name"></span>
                                    Топливо:
                                    <span ng-bind=" ctrl.reference.fuel.name"></span>
                                </div>
                                <div class="col-md-1">
                                    <input type="number" ng-model="ctrl.amount" placeholder="л." ng-change="ctrl.onFillForm()"
                                           title="Кратно 20 л.">
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-secondary">Закрыть</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                        <button type="button" class="btn btn-primary">Добавить</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/controller/order_page_controller.js' />"></script>
    </body>
</html>
