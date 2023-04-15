# Naive implementation of Web-crawler based on ForkJoinPool

There is two methods:
- Start indexing based on url of the site you want to crawl. Crawler process links that pelogs to the site base address.
- Stop indexing removes forked tasks from in-memorhy lists
