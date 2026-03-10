package com.example.mybatisadvanced.handler;

import java.awt.List;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@MappedTypes(List.class)
public class JsonStrTypeHandler extends BaseTypeHandler<ArrayList<String>>{
    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ArrayList<String> parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, toString(parameter));
    }

    @Override
    public ArrayList<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);

        ArrayList<String> result = new ArrayList<String>();
        String[] jsonArray = str.split(",");
        for(String jsonstr: jsonArray) { 
            result.add(jsonstr);
        }
        return result;
    }

    @Override
    public ArrayList<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);

        ArrayList<String> result = new ArrayList<String>();
        String[] jsonArray = str.split(",");
        for(String jsonstr: jsonArray) { 
            result.add(jsonstr);
        }
        return result;
    }

    @Override
    public ArrayList<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);

        ArrayList<String> result = new ArrayList<String>();
        String[] jsonArray = str.split(",");
        for(String jsonstr: jsonArray) { 
            result.add(jsonstr);
        }
        return result;
    }

    private String toString(ArrayList<String> arrayList) {
        try {
            return OBJECT_MAPPER.writeValueAsString(arrayList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
