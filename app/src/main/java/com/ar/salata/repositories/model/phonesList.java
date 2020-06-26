package com.ar.salata.repositories.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class phonesList {
    private Map<String, String> phones;

    public List<String> getPhones() {
        return new ArrayList<>(phones.values());
    }
}
