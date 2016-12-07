package net.oschina.app.improve.bean;

import java.io.Serializable;

/**
 * Created by haibin
 * on 2016/12/5.
 */

public class SignUpEventOptions implements Serializable {
    public static final String FORM_TYPE_TEXT = "text";
    public static final String FORM_TYPE_TEXT_AREA = "textarea";
    public static final String FORM_TYPE_SELECT = "select";
    public static final String FORM_TYPE_CHECK_BOX = "checkbox";
    public static final String FORM_TYPE_RADIO = "radio";
    public static final String FORM_TYPE_EMAIL = "email";
    public static final String FORM_TYPE_DATE = "date";
    public static final String FORM_TYPE_MOBILE = "mobile";
    public static final String FORM_TYPE_NUMBER = "number";
    public static final String FORM_TYPE_URL = "url";

    private String key;
    private String value;//用户输入的参数
    private int keyType;
    private int formType;
    private String label;
    private String option;
    private String optionStatus;
    private String defaultValue;
    private boolean required;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public int getFormType() {
        return formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(String optionStatus) {
        this.optionStatus = optionStatus;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}