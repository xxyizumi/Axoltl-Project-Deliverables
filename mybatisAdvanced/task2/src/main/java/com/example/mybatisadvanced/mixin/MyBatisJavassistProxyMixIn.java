package com.example.mybatisadvanced.mixin;

import org.apache.ibatis.javassist.util.proxy.MethodHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface MyBatisJavassistProxyMixIn {
	@JsonIgnore MethodHandler getHandler(); // @JsonIgnoreを付与してhandlerプロパティをシリアライズ対象外に指定する
}
