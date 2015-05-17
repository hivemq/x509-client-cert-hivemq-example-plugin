/*
 * Copyright 2013 dc-square GmbH
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

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.OnConnectCallback;
import com.dcsquare.hivemq.spi.callback.exception.RefusedConnectionException;
import com.dcsquare.hivemq.spi.message.CONNECT;
import com.dcsquare.hivemq.spi.message.ReturnCode;
import com.dcsquare.hivemq.spi.security.ClientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


public class ClientConnectCallback implements OnConnectCallback {

    private static final Logger log = LoggerFactory.getLogger(ClientConnectCallback.class);


    @Override
    public void onConnect(final CONNECT connect, final ClientData clientData) throws RefusedConnectionException {

        //Throw out clients which didn't provide a client certificate
        if (!clientData.getCertificate().isPresent()) {
            log.error("Client {} didn't provide a X509 client certificate. Disconnecting client", clientData.getClientId());
            throw new RefusedConnectionException(ReturnCode.REFUSED_NOT_AUTHORIZED);
        }
        log.info("Client {} connected with X509 client certificate", clientData.getClientId());

        final Certificate certificate = clientData.getCertificate().get();

        //We need to downcast to the X509 certificate
        if (certificate instanceof X509Certificate) {

            final X509Certificate x509Certificate = (X509Certificate) certificate;
            log.info("X509 client certificate provided by client {} has the serial number {}",
                    clientData.getClientId(), x509Certificate.getSerialNumber());
        }

    }


    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
