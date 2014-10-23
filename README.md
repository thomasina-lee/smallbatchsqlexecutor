smallbatchsqlexecutor
=====================

# Purpose

Imagine, you need to run a group by query on a big tables.  While it could just be a simple SQL, you don't want 
to hit it in production where it will soak up all the memory and impact your customer.  Also, depending on where 
your indexes are on the table, and the efficiency of the query optimizing engine, it could also be doing a lot of 
sorting, which does not grow linearly with data size.  Also, usually there is no indication how much longer the query 
going to take.

This utility is created to help running relational database sql queries in batches, typically group-by queries, with 
the aim to:

* reduce memory requirement to run the query and hopefully improve efficiency
* to have steady feedback of the progress of the query task


## To Run an Example
(assuming mysql and java installed, and database 'test' is free to be used)

```shell
$ cd $(THIS_PROJECT_FOLDER)
$ mvn package
$ mysql < example/example.sql
$ java -jar target/small-batch-sql-executor-1.0.0.jar -y example/run.yaml 
```

## Compile
The following command should compile the 
```shell
$ mvn package
```
and the bundled jar will be found in `target/small-batch-sql-executor-1.0.0.jar`





## Example Configuration Content

Example (as shown in example/run.yaml):
```yaml
dbDriver: 
  driverClassName: com.mysql.jdbc.Driver
  driverUrl: file:///tmp/mysql-connector-java-5.1.33-bin.jar
executor: !iterating_executor
  dbConnStr: jdbc:mysql://localhost/test
  dbPassword: ""
  dbUserName: ""
  queryIterator: !int_iterator
    interval: 3
    startId: 0
    endId: 12
    sql: |
          select
          date_number,
          group_name, 
          sum(val) as sum_val
          from  example_table
          where date_number >= ? and date_number< ?
          group by date_number, group_name
          
  resultSetProcess: !delimited_processor 
    delimiter: "\t" 
    filename: /tmp/example_result.txt
    lineDelimiter: "\n"
    quote: ''


```
