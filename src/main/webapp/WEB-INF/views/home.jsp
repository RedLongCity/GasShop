<%-- 
    Document   : home
    Created on : 01.11.2017, 17:47:49
    Author     : redlongcity
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div class="col-md-12 col-lg-12 schedule-table">
                    <div class="card text-center">
                        <h2 class="card-title">Цены на топливо</h2>
                        <div class="table-responsive" ng-controller="HomeController as ctrl">
                            <table class="table text-center table-striped">
                                <thead>
                                    <tr>

                                        <th>AЗС</th>

                                        <th ng-repeat="f in ctrl.fuels"><span ng-bind="f"></span></th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="s in ctrl.stations">
                                        <td><span ng-bind="s"></span></td>
                                        <td ng-repeat="ci in ctrl.table_content[$index]">
                                            <span ng-bind="ci"></span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <button class="btn btn-success">Сделать заказ</button>
                    </div>
                </div>
            </section>

        </main>


        <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/services/gas_station_service.js' />"></script>
        <script src="<c:url value='/static/js/services/fuel_service.js' />"></script>
        <script src="<c:url value='/static/js/services/reference_service.js' />"></script>
        <script src="<c:url value='/static/js/controller/home_controller.js' />"></script>

    </body>
</html>
