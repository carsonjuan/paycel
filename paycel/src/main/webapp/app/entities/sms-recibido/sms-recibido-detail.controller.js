(function() {
    'use strict';

    angular
        .module('paycelApp')
        .controller('SmsRecibidoDetailController', SmsRecibidoDetailController);

    SmsRecibidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsRecibido'];

    function SmsRecibidoDetailController($scope, $rootScope, $stateParams, entity, SmsRecibido) {
        var vm = this;
        vm.smsRecibido = entity;
        vm.load = function (id) {
            SmsRecibido.get({id: id}, function(result) {
                vm.smsRecibido = result;
            });
        };
        var unsubscribe = $rootScope.$on('paycelApp:smsRecibidoUpdate', function(event, result) {
            vm.smsRecibido = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
