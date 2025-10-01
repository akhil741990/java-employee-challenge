package com.reliaquest.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponse {
    private List<Employee> data;
    public List<Employee> getData() {
        return data;
    }
    public void setData(List<Employee> data) {
        this.data = data;
    }
}