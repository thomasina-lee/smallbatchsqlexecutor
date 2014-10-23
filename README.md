smallbatchsqlexecutor
=====================

# Purpose

This is a utility to help running relational database sql queries in batches, typically group-by queries,  
with the aim to:

* reduce memery requirement to run the query and hopefully improve efficiency
* to have steady feedback of the progress of the query task


## Compile
The following command should compile the 
```shell
$ mvn package
```
and the 


## To run an example
(assuming mysql and java installed, and database 'test' is free to be used)

```shell
$ cd $(THIS_PROJECT_FOLDER)
$ mvn package
$ mysql < example/example.sql
$ java -jar target/small-batch-sql-executor-1.0.0.jar -y example/run.yaml 
```


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
