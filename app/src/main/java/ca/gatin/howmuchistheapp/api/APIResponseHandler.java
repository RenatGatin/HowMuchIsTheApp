package ca.gatin.howmuchistheapp.api;

import ca.gatin.howmuchistheapp.model.ActionError;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public interface APIResponseHandler {
    public void postExecuteAPISuccess(ActionType callType, Object data);
    public void postExecuteAPIFailure(ActionType callType, ActionError error);
}
