var sModule = angular.module('ServicesModule', []);

sModule.service('linkService', function($rootScope,$http, $q){

		this.getLinks = function(query1, nick1){
			var deferred = $q.defer();
			console.log("query1:"+query1);
			$http.post('/SearchInFarooAgent/search?nick='+nick1+"&query="+query1).success(function(data) {
				console.log(data);
			    			  deferred.resolve(data);
			    		  }).
			    		  error(function(data, status, headers, config) {
			    			  alert(data);
			    				$rootScope.errorMessage = data;
			    				$rootScope.showErrorMessage = true;
			    				deferred.reject(data);
			    		  });
				return deferred.promise;
		}
		
		this.getCollSortedLinks = function(query1, nick1){
			var deferred = $q.defer();
			$http.post('/CollaborativeSearchAgent/collsearch?nick='+nick1+"&query="+query1).success(function(data) {
			    			  deferred.resolve(data);
			    		  }).
			    		  error(function(data, status, headers, config) {
			    			  alert(data);
			    				$rootScope.errorMessage = data;
			    				$rootScope.showErrorMessage = true;
			    				deferred.reject(data);
			    		  });
				return deferred.promise;
			
			
		}
			
		this.sendGrades = function(grades1, nick){
			var deferred = $q.defer();
			$http.post('/'+nick+'/grades', grades1).success(function(data) {
				console.log(grades1);
	  			  deferred.resolve(grades1);
	  		  }).
	  		  error(function(data, status, headers, config) {
	  			    alert(data);
	  				$rootScope.errorMessage = data;
	  				$rootScope.showErrorMessage = true;
	  				deferred.reject(data);
	  		  });
			return deferred.promise;
			
		}
});