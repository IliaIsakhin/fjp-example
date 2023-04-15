package org.example;

import org.example.domain.IndexingTaskContext;
import org.example.domain.Site;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class IndexingManager {
    
    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private final Set<String> visitedPages = new ConcurrentSkipListSet<>();
    private final List<IndexingRecursiveTask> tasks = new ArrayList<>();
    
    public IndexingResponse startIndexing(Site site) {
        var task = new IndexingRecursiveTask(site.getName(), new IndexingTaskContext(visitedPages, tasks));

        var result = forkJoinPool.invoke(task);
        
        return new IndexingResponse(result);
    }
    
    public void stopIndexing() {
        for (IndexingRecursiveTask task : tasks) {
            task.cancel(true);
        }
    }
}
