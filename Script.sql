create table if not exists "users" (
	id bigserial not null unique primary key,
	"name" varchar(60) not null,
	email varchar(60) not null unique,
	is_active boolean not null default true,
	"token" varchar(255) unique,
	"password" varchar(100) not null,
	created_at timestamp not null,
	updated_at timestamp not null
);

create table if not exists accounts (
	id bigserial not null unique primary key,
	"name" varchar(60) not null,
	created_at timestamp not null,
	updated_at timestamp not null,
	user_id bigint not null,
	foreign key (user_id) references users(id)
);

create type notification_type as enum(
	'death', 'disinheritance', 'invite'
);

create table if not exists notifications (
	id bigserial not null unique primary key,
	"type" notification_type not null,
	created_at timestamp not null,
	account_id bigint not null,
	foreign key (account_id) references accounts(id)
);

create table if not exists accounts_notification (
	id bigserial not null unique primary key,
	is_read boolean not null default false,
	account_id bigint not null,
	notification_id bigint not null,
	foreign key (account_id) references accounts(id),
	foreign key (notification_id) references notifications(id)
);

create table if not exists owners (
	id bigserial not null unique primary key,
	is_alive boolean not null default true,
	account_id bigint not null,
	foreign key (account_id) references accounts(id)
);

create type heir_status as enum('active', 'accepted', 'disinherited');

create table if not exists heirs (
	id bigserial not null unique primary key,
	status heir_status not null default 'active',
	owner_id bigint not null,
	account_id bigint not null,
	foreign key (owner_id) references owners(id),
	foreign key (account_id) references accounts(id)
);

create type file_type as enum('document', 'image', 'video');

create table if not exists files (
	id bigserial not null unique primary key,
	"name" varchar(60) not null,
	description varchar(200),
	url varchar(100) not null,
	"type" file_type not null,
	"extension" varchar(6) not null,
	owner_id bigint not null,
	created_at timestamp not null,
	updated_at timestamp not null,
	foreign key (owner_id) references owners(id)
);

create table if not exists files_heirs (
	id bigserial not null unique primary key,
	file_id bigint not null,
	heir_id bigint not null,
	foreign key (file_id) references files(id),
	foreign key (heir_id) references heirs(id)
);

create table if not exists logs (
	id bigserial not null unique primary key,
	content text,
	created_at timestamp not null
);

