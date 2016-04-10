(function() {
    'use strict';

    angular
        .module('paycelApp')
        .controller('SmsRecibidoDialogController', SmsRecibidoDialogController);

    SmsRecibidoDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SmsRecibido'];

    function SmsRecibidoDialogController ($scope, $stateParams, $uibModalInstance, entity, SmsRecibido) {
        var vm = this;
        vm.smsRecibido = entity;
        vm.load = function(id) {
            SmsRecibido.get({id : id}, function(result) {
                vm.smsRecibido = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('paycelApp:smsRecibidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.smsRecibido.id !== null) {
                SmsRecibido.update(vm.smsRecibido, onSaveSuccess, onSaveError);
            } else {
                SmsRecibido.save(vm.smsRecibido, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.fechaHoraRecibido = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
