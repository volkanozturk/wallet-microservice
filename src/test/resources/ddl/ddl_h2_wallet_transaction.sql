
drop table if exists `wallet` CASCADE ;
drop table if exists `transaction` CASCADE ;
drop table if exists api_log CASCADE;

create table wallet (
						id bigint not null,
						created_at timestamp not null,
						last_updated_at timestamp not null,
						balance numeric(19,2) not null,
						player_id varchar(190) not null,
						version bigint,
						primary key (id)
);

create table transaction (
							 id bigint not null,
							 created_at timestamp not null,
							 last_updated_at timestamp not null,
							 amount numeric(19,2) not null,
							 transaction_id varchar(255) not null,
							 type varchar(255),
							 wallet_id bigint,
							 primary key (id)
);

create table api_log (
						 id bigint not null,
						 call_date timestamp,
						 end_point varchar(255) not null,
						 exception_message varchar(1024),
						 http_request_method varchar(255) not null,
						 http_status_code integer,
						 request_payload varchar(1024),
						 response_payload varchar(1024),
						 source_ip_address varchar(255),
						 total_duration bigint,
						 primary key (id)
);

create index transaction_transaction_id on transaction (transaction_id);
create index transaction_wallet_id on transaction (wallet_id);
create index wallet_player_id on wallet (player_id);
alter table transaction
	add constraint transaction_transaction_id unique (transaction_id);


