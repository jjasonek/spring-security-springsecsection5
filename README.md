# Udemy Course Spring Security Section5
## spring version: 3.4.4

## Docker container for MySQL
docker run -p 3306:3306 --name springsecurity -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=eazybank -d mysql
and also download a client SQLELECTRON from https://sqlectron.github.io/


## prepare the database
//users.ddl found in :
public static final String DEFAULT_USER_SCHEMA_DDL_LOCATION = "org/springframework/security/core/userdetails/jdbc/users.ddl";

create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

//after adaptation for MySQL
create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

We also added the date in the tables.


## Make it work
We added also Spring Data JPA dependency in pom.xml, which we will need later, not just now.


## Utilize Custom tables

### table
CREATE TABLE `customer` (
`id` int NOT NULL AUTO_INCREMENT,
`email` varchar(45) NOT NULL,
`pwd` varchar(200) NOT NULL,
`role` varchar(45) NOT NULL,
PRIMARY KEY (`id`)
);

### data
INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('happy@example.com', '{noop}EazyBytes@12345', 'read');
INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('admin@example.com', '{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m', 'admin');
EazyBytes@54321


## Using users from the custom table.
Rather then implementing the UserDetailsManager with many methods to implement, 
we just implement the UserDetailsService interface with only one method: loadUserByUsername(), 
which is the one we just need.

And we can see following formated select in the log:
Hibernate:
    select
        c1_0.id,
        c1_0.email,
        c1_0.pwd,
        c1_0.role
    from
        customer c1_0
    where
        c1_0.email=?


## Register user using the REST API
After adding the "/register" endpoint to the public APIs in the SecurityConfig, 
we get HTTP 403 after request

POST
{
    "email": "john@example.com",
    "pwd": "EazyBytes@12345",
    "role": "user"
}

This happens because Spring Security framework by default STOPS requests modifying/deleting/inserting data 
due to the CSRF protection.

For now, we have solved this by disabling the CSRF protection in the SecurityConfig.
