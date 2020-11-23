package com.seezoon.generator.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 前端表单类型
 *
 * @author hdf
 */
public enum InputType {

    TEXT("text"),

    SELECT("select"),

    HIDDEN("hidden"),
    // 整数
    INTEGRAL_NUMBER("integral_number"),
    // 小数
    DECIMAL("decimal"),

    CHECKBOX("checkbox"),

    RADIO("radio"),

    DATE("date"),

    DATETIME("datetime"),

    TEXTAREA("textarea"),

    RICHTEXT("richtext"),

    IMAGE("image"),

    FILE("file"),;

    private static final Map<String, InputType> inputTypes = new HashMap<>();

    static {
        Arrays.stream(InputType.values()).forEach((v) -> {
            inputTypes.put(v.inputName(), v);
        });
    }

    /**
     * 名称
     */
    private String inputName;

    InputType(String inputName) {
        this.inputName = inputName;
    }

    public static InputType parse(String inputName) {
        InputType inputType = inputTypes.get(inputName);
        Assert.isTrue(null != inputType,
            String.format("inputName[%s] not support,pls supplement enum InputType", inputName));
        return inputType;
    }

    public String inputName() {
        return inputName;
    }
}
