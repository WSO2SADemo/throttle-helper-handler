package org.wso2.carbon.apim.handler;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.wso2.carbon.apimgt.gateway.handlers.throttling.APIThrottleConstants;

import java.util.HashMap;
import java.util.Map;

public class ThrottleHelperHandler extends AbstractHandler {
    public boolean handleRequest(MessageContext messageContext) {
        Map headers = getTransportHeaders(messageContext);
        String client_id = "empty_client_id";
        if (headers.get("client_id") != null) {
            client_id = headers.get("client_id").toString();
        }
        HashMap<String, Object> propertyFromMsgCtx;
        if (messageContext.getProperty(APIThrottleConstants.CUSTOM_PROPERTY) !=  null) {
            propertyFromMsgCtx = (HashMap<String, Object>) messageContext.getProperty(
                    APIThrottleConstants.CUSTOM_PROPERTY);
        } else {
            propertyFromMsgCtx = new HashMap<>();
        }
        propertyFromMsgCtx.put("client_id_throttle_property", client_id);
        messageContext.setProperty(APIThrottleConstants.CUSTOM_PROPERTY, propertyFromMsgCtx);
        return true;
    }

    public boolean handleResponse(MessageContext messageContext) {
        return true;
    }

    private Map getTransportHeaders(MessageContext messageContext) {
        return (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
    }

}
