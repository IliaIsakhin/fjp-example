package org.example.domain;

import java.util.List;

public class Page {
    
    private String name;
    private List<Page> references;

    public Page(String name, List<Page> references) {
        this.name = name;
        this.references = references;
    }

    public String getName() {
        return name;
    }

    public List<Page> getReferences() {
        return references;
    }
}
