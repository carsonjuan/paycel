(function() {
    'use strict';

    angular
        .module('paycelApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sms-recibido', {
            parent: 'entity',
            url: '/sms-recibido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'paycelApp.smsRecibido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sms-recibido/sms-recibidos.html',
                    controller: 'SmsRecibidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('smsRecibido');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sms-recibido-detail', {
            parent: 'entity',
            url: '/sms-recibido/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'paycelApp.smsRecibido.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sms-recibido/sms-recibido-detail.html',
                    controller: 'SmsRecibidoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('smsRecibido');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SmsRecibido', function($stateParams, SmsRecibido) {
                    return SmsRecibido.get({id : $stateParams.id});
                }]
            }
        })
        .state('sms-recibido.new', {
            parent: 'sms-recibido',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-recibido/sms-recibido-dialog.html',
                    controller: 'SmsRecibidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroTelefono: null,
                                mensaje: null,
                                fechaHoraRecibido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sms-recibido', null, { reload: true });
                }, function() {
                    $state.go('sms-recibido');
                });
            }]
        })
        .state('sms-recibido.edit', {
            parent: 'sms-recibido',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-recibido/sms-recibido-dialog.html',
                    controller: 'SmsRecibidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SmsRecibido', function(SmsRecibido) {
                            return SmsRecibido.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sms-recibido', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sms-recibido.delete', {
            parent: 'sms-recibido',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sms-recibido/sms-recibido-delete-dialog.html',
                    controller: 'SmsRecibidoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SmsRecibido', function(SmsRecibido) {
                            return SmsRecibido.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sms-recibido', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
