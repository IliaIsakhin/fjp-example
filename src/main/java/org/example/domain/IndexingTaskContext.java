package org.example.domain;

import org.example.IndexingRecursiveTask;

import java.util.List;
import java.util.Set;

public record IndexingTaskContext(Set<String> visitedPages, List<IndexingRecursiveTask> tasks) {
}
