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

package com.hivemq.plugin.x509example.callbacks;

import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.security.OnAuthorizationCallback;
import com.hivemq.spi.callback.security.authorization.AuthorizationBehaviour;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.topic.MqttTopicPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationCallback implements OnAuthorizationCallback {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationCallback.class);

    @Override
    public List<MqttTopicPermission> getPermissionsForClient(final ClientData clientData) {

        final List<MqttTopicPermission> permissions = new ArrayList<>();

        final X509Certificate certificate = (X509Certificate) clientData.getCertificate().get();

        if ("dc-square".equals(certificate.getIssuerDN().getName())) {
            log.info("Issuer was dc-square, client is allowed to use all topics with all permissions");
            permissions.add(new MqttTopicPermission("#", MqttTopicPermission.TYPE.ALLOW));
        }

        return permissions;
    }

    @Override
    public AuthorizationBehaviour getDefaultBehaviour() {
        return AuthorizationBehaviour.NEXT;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
