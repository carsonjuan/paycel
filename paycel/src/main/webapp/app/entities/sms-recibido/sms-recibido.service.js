(function() {
    'use strict';
    angular
        .module('paycelApp')
        .factory('SmsRecibido', SmsRecibido);

    SmsRecibido.$inject = ['$resource', 'DateUtils'];

    function SmsRecibido ($resource, DateUtils) {
        var resourceUrl =  'api/sms-recibidos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fechaHoraRecibido = DateUtils.convertDateTimeFromServer(data.fechaHoraRecibido);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
