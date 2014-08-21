smallbatchsqlexecutor
=====================

# Purpose

This is a utility to help breaking down relational database sql queries, typically group-by queries,  
typically into batches by date or by customer id, with the aim to :

* reduce memery requirement to run the query
* to have steady feedback of the progress of the query task
