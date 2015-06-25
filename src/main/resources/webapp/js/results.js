function ResultsController($scope, $rootScope, $http, $location, linkService, $cookieStore, $routeParams, $sessionStorage) {
	 $scope.nick =$sessionStorage.nick;
	 $scope.query=$sessionStorage.query;

		$scope.search = function(){	
			var promise = linkService.getLinks($scope.query, $scope.nick);
	
			promise.then(function(data){
				$scope.links = data;		
			});
		};
		
		$scope.collSearch = function(){
			var promise = linkService.getCollSortedLinks($scope.query, $scope.nick);
			
			promise.then(function(data){
				$scope.links = data;				
			});
		}
		
		$scope.sendGrades = function(){
			$scope.links.query = $scope.query;
			console.log($scope.links);
			var promise = linkService.sendGrades($scope.links,$scope.nick );
			
			promise.then(function(data){
				alert("Your grades have been sent.")			
			});
		};
		
		$scope.back = function(){
			$location.path('/webseeker/');
		}
};

 
 
