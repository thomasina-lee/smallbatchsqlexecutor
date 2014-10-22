
use test;

create table example_table(
  id INTEGER PRIMARY KEY,
  date_number INTEGER,
  group_name TEXT,
  val NUMERIC

);


INSERT INTO example_table
(id, date_number, group_name, val)
values
(1, 1, 'a', 12);


INSERT INTO example_table
(id, date_number, group_name, val)
values
(2, 1, 'a', 15);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(3, 1, 'b', 18);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(4, 1, 'b', 9);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(5, 2, 'a', 5);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(6, 2, 'b', 9);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(7, 2, 'a', 2);

INSERT INTO example_table
(id, date_number, group_name, val)
values
(8, 2, 'b', 9);