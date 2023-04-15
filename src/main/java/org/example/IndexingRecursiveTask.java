package org.example;

import org.example.domain.IndexingTaskContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndexingRecursiveTask extends RecursiveTask<List<String>> {
    public static final Logger logger = Logger.getLogger(IndexingRecursiveTask.class.getName());
    
    private final String currentUrl;
    private final IndexingTaskContext context;
    private final LinkExtractor linkExtractor = new LinkExtractor();
    
    public IndexingRecursiveTask(String url, IndexingTaskContext context) {
        this.context = context;
        this.currentUrl = url;
    }
    
    @Override
    protected List<String> compute() {
        logger.log(Level.INFO, "Started compute " + currentUrl);
        
        List<String> result = new ArrayList<>();
        result.add(currentUrl);
        context.visitedPages().add(currentUrl);
        List<String> extractedUrls = linkExtractor.extract(currentUrl);

        extractedUrls.stream()
                .filter(u -> !context.visitedPages().contains(u))
                .map(u -> new IndexingRecursiveTask(u, context))
                .map(t -> {
                    context.tasks().add(t);
                    return t.fork().join();
                })
                .forEach(result::addAll);

        logger.log(Level.INFO, "Finished compute");

        return result;
    }
}
