(function() {
    'use strict';

    angular
        .module('paycelApp')
        .controller('SmsRecibidoDeleteController',SmsRecibidoDeleteController);

    SmsRecibidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'SmsRecibido'];

    function SmsRecibidoDeleteController($uibModalInstance, entity, SmsRecibido) {
        var vm = this;
        vm.smsRecibido = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            SmsRecibido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
