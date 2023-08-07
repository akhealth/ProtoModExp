/*
This file is used by the dockerfile to initialize the tables in our db. 
JPA supposedly can do this automagically without the script but so far haven't got that working.
*/


/*This block is generated from the entities in our Springboot App. */
create sequence ar_application_for_aid_seq start with 1 increment by 50;
create sequence dc_cases_seq start with 1 increment by 50;
create sequence dc_indv_seq start with 1 increment by 50;
create table ar_app_indv (app_num varchar(255) not null, indv_id bigint not null, head_of_household_sw char(1), primary key (app_num, indv_id));
create table ar_application_for_aid (app_num varchar(255) not null, application_status_cd varchar(255), app_recvd_dt timestamp(6), primary key (app_num));
create table dc_cases (case_num bigint not null, case_mode_cd varchar(255), case_status_cd varchar(255), primary key (case_num));
create table dc_indv (indv_id bigserial not null, dob_dt timestamp(6), first_name varchar(255), last_name varchar(255), ssn varchar(255), primary key (indv_id));
alter table if exists ar_app_indv add constraint FK5rl5yvbwoyn3xdqet3lo1scf7 foreign key (app_num) references ar_application_for_aid;
alter table if exists ar_app_indv add constraint FKnm83ef1059aws7r15nxlyohav foreign key (indv_id) references dc_indv;

/* Just a dummy entry to make sure everything is working before I figure out how to generate mock data */
insert into dc_indv (indv_id, first_name, last_name, dob_dt, ssn)
values 
(1, 'Generic', 'Person', '1965-01-15 00:00:00', '000000001'), 
(2, 'Generic jr.', 'Person', '1993-09-01 00:00:00', '000000002'),
(3, 'Catherine', 'Person', '1968-07-04 00:00:00', '000000003'),
(4, 'Fred', 'Flintstone', '1955-02-10 00:00:00', '000000004'),
(5, 'Scooby', 'Doo', '1969-09-13 00:00:00', '000000005'),
(6, 'Shaggy', 'Rogers', '1969-09-13 00:00:00', '000000006'),
(7, 'Bruce', 'Wayne', '1915-04-07 00:00:00', '000000007'),
(8, 'Clark', 'Kent', '1977-04-18 00:00:00', '000000008');

insert into ar_application_for_aid (app_num, application_status_cd, app_recvd_dt)
values 
('T00000001', 'GOODSTATUS_CD', '2000-06-06 00:00:00'),
('T00000002', 'BADSTATUS_CD', '1970-08-06 00:00:00'),
('T00000003', 'NEEDAID_CD', '1980-09-13 00:00:00'),
('T00000004', 'TOOMUCHMONEY_CD', '1970-08-06 00:00:00'),
('T00000005', 'NOTCITIZEN_CD', '1970-08-06 00:00:00');

insert into ar_app_indv (app_num, indv_id, head_of_household_sw)
values 
('T00000001',1, 'Y'), 
('T00000001',2, 'N'),
('T00000001',3, 'N'),
('T00000002',4, 'Y'),
('T00000003',5, 'N'),
('T00000003',6, 'Y'),
('T00000004',7, 'Y'),
('T00000005',8, 'Y');

SELECT setval('dc_indv_seq', (SELECT MAX(indv_id) FROM dc_indv));
 /* SELECT setval('ar_application_for_aid_seq', (SELECT MAX(app_num) FROM ar_application_for_aid)+1); */

