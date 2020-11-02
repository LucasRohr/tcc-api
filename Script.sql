create table if not exists "users" (
	id bigserial not null unique primary key,
	"name" varchar(60) not null,
	email varchar(60) not null unique,
	is_active boolean not null default true,
	"token" varchar(255) unique,
	login_token varchar(255) unique,
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
	"type" varchar(15) not null,
	foreign key (user_id) references users(id)
);

create table if not exists notifications (
	id bigserial not null unique primary key,
	"type" varchar(25) not null,
	created_at timestamp not null,
	account_id bigint not null,
	foreign key (account_id) references accounts(id)
);

create table if not exists accounts_notification (
	id bigserial not null unique primary key,
	is_read boolean not null default false,
	receiver_id bigint not null,
	notification_id bigint not null,
	foreign key (receiver_id) references accounts(id),
	foreign key (notification_id) references notifications(id)
);

create table if not exists owners (
	id bigserial not null unique primary key,
	is_alive boolean not null default true,
	account_id bigint not null,
	foreign key (account_id) references accounts(id)
);

create table if not exists heirs (
	id bigserial not null unique primary key,
	status varchar(15) not null default 'active',
	owner_id bigint not null,
	account_id bigint not null,
	foreign key (owner_id) references owners(id),
	foreign key (account_id) references accounts(id)
);

create table if not exists invites (
	id bigserial not null unique primary key,
	status varchar(15) not null default 'pending',
	owner_id bigint not null,
	receiver_id bigint not null
);

create table if not exists files (
	id bigserial not null unique primary key,
	"name" varchar(60) not null,
	description varchar(200),
	bucket_url varchar(100) not null,
	"type" varchar(15) not null,
	mime_type varchar(6) not null,
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


-- remover depois


ALTER TABLE invites ALTER COLUMN receiver_id DROP NOT NULL

ALTER TABLE invites
ADD COLUMN invite_code varchar(6);


INSERT into heirs (status, owner_id, account_id) values ('active', 9, 9)

insert into files ("name" , description, bucket_url, "type", mime_type, owner_id, created_at, updated_at) values
	('arquivo', '', '', 'image', 'png', 9, '2001-08-13T03:00:00', '2001-08-13T03:00:00')

insert into files_heirs (file_id, heir_id) values (1, 1)



