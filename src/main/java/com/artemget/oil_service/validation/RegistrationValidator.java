package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

import java.util.regex.Matcher;

import static com.artemget.oil_service.utils.PatternUtil.EMAIL_PATTERN;

public class LoginValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        var body = event.getBodyAsJson();
        Preconditions.checkArgument(!body.getString("name").isEmpty());
        Preconditions.checkArgument(!body.getString("password").isEmpty());
        Preconditions.checkArgument(!body.getString("email").isEmpty());

        Matcher matcher = EMAIL_PATTERN.matcher(body.getString("email"));
        Preconditions.checkArgument(matcher.find());
    }
}
