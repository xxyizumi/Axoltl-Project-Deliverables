package com.example.mybatisadvanced.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class JsonIgnorePlugin extends PluginAdapter {

    public JsonIgnorePlugin() {
        
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
            IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
            ModelClassType modelClassType) {
        // パスワードフィールドに@JsonIgnoreを追加
        if ("password".equals(field.getName())) {
            field.addAnnotation("@JsonIgnore");
        }
        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}