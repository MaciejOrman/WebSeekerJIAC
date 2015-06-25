function SearchController($scope, $rootScope, $http, $location, linkService, $cookieStore, $sessionStorage) {
	$scope.query = "java";
	$scope.nick = "Maciej";
	
		$scope.search = function(){	
			$sessionStorage.nick = $scope.nick;
			$sessionStorage.query = $scope.query;
			$location.path('/webseeker/results');	
		};
		
		$scope.collSearch = function(){
			var promise = linkService.getSortedLinks($scope.query, $scope.nick);
			
			promise.then(function(data){
				$scope.links = data;
				$location.path('/webseeker/results');
				
			});
		}
		
		$scope.sendGrades = function(){
			$scope.links.query = $scope.query;
			console.log($scope.links);
			var promise = linkService.sendGrades("{reccomendedLinks:"+$scope.links[1]+"}");
			
			promise.then(function(data){
				alert("Your grades have been sent.")			
			});
		};
		
		$scope.back = function(){
			$location.path('/webseeker');
		};
};

 
 
