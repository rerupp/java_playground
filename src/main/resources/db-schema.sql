drop table ACCOUNT if exists;

create table ACCOUNT(ID bigint identity primary key,
                      ACCOUNT varchar(9),
                      OWNER varchar(50) not null,
                      BALANCE decimal(8, 2),
                      unique(ACCOUNT));
                     
alter table ACCOUNT alter column BALANCE set default 0.0;
