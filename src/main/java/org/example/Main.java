package org.example;

import org.example.domain.Site;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        var site1 = new Site("https://ihateregex.io");
        IndexingManager indexingManager = new IndexingManager();
        
        var response = indexingManager.startIndexing(site1);

        logger.log(Level.INFO, response.result().toString());
    }
}
