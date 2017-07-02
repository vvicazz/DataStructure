var myApp = angular.module("myModule", []);

myApp.controller("myController", function($scope) {
	$scope.message1 = "hello to angular1";
	$scope.message2 = "hello to angular2";
});