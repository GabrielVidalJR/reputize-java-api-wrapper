package org.reputize.api.request;

import org.json.JSONObject;
import org.reputize.api.request.type.RequestType;

public interface Request {

    String BASE_URL = "https://www.reputize.org/api/v1/";

    String getCategory();
    RequestType getRequestType();
    JSONObject fetchData(final String value);
}
