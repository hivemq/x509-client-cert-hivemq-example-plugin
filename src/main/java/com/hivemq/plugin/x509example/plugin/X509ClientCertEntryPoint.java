/*
 * Copyright 2015 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.plugin.x509example.plugin;

import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.callback.registry.CallbackRegistry;
import com.hivemq.plugin.x509example.callbacks.AuthorizationCallback;
import com.hivemq.plugin.x509example.callbacks.ClientConnectCallback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class X509ClientCertEntryPoint extends PluginEntryPoint {

    private final AuthorizationCallback authorizationCallback;
    private final ClientConnectCallback clientConnectCallback;


    @Inject
    public X509ClientCertEntryPoint(final AuthorizationCallback authorizationCallback,
                                    final ClientConnectCallback clientConnectCallback) {

        this.authorizationCallback = authorizationCallback;
        this.clientConnectCallback = clientConnectCallback;
    }

    @PostConstruct
    public void postConstruct() {

        CallbackRegistry callbackRegistry = getCallbackRegistry();

        callbackRegistry.addCallback(authorizationCallback);
        callbackRegistry.addCallback(clientConnectCallback);

    }


}
