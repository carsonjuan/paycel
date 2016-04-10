'use strict';

describe('Controller Tests', function() {

    describe('SmsRecibido Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSmsRecibido;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSmsRecibido = jasmine.createSpy('MockSmsRecibido');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SmsRecibido': MockSmsRecibido
            };
            createController = function() {
                $injector.get('$controller')("SmsRecibidoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'paycelApp:smsRecibidoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
