package com.hivemq.plugin.x509example.callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.security.OnAuthorizationCallback;
import com.dcsquare.hivemq.spi.security.ClientData;
import com.dcsquare.hivemq.spi.topic.MqttTopicPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationCallback implements OnAuthorizationCallback {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationCallback.class);

    @Override
    public List<MqttTopicPermission> getPermissionsForClient(final ClientData clientData) {

        final List<MqttTopicPermission> permissions = new ArrayList<MqttTopicPermission>();

        final X509Certificate certificate = (X509Certificate) clientData.getCertificate().get();

        if ("dc-square".equals(certificate.getIssuerDN().getName())) {
            log.info("Issuer was dc-square, client is allowed to use all topics with all permissions");
            permissions.add(new MqttTopicPermission("#", MqttTopicPermission.ALLOWED_QOS.ALL));
        }

        return permissions;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
