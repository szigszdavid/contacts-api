create table contact
(
    id  bigint generated by default as identity,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    emailAddress VARCHAR(200),
    phoneNumber VARCHAR(200),
    comment VARCHAR(300),
    constraint pk_contact primary key (id)
);