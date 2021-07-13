package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.Oil;
import io.vertx.core.json.JsonObject;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OilParser {
    public static JsonObject toJson(List<Oil> oilList) {
        return new JsonObject().put("oils",
                oilList.stream().map(oil ->
                        new JsonObject()
                                .put("name", oil.getName())
                                .put("output_place", oil.getOutputPlace())
                                .put("p20", oil.getDensity20())
                                .put("p50", oil.getDensity50())
                                .put("v20", oil.getViscosity20())
                                .put("v50", oil.getViscosity50())
                                .put("hk_350", oil.getHk350()))
                        .collect(Collectors.toList()));
    }
}