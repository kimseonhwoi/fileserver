drop table file_list;

create table file_list(
                          sn int auto_increment primary key ,
                          fileName varchar(500),
                          fileUuidName varchar(100),
                          content varchar(500),
                          useYn char(1)
);
