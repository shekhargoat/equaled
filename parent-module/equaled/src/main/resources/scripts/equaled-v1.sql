create table accounts
(
    id              int auto_increment comment 'Primary Key'
        primary key,
    sid             binary(32)                         not null,
    name            varchar(100)                       not null,
    description     text                               null,
    enabled         int      default 1                 null,
    created_on      datetime default CURRENT_TIMESTAMP not null,
    last_updated_on datetime                           null,
    constraint accounts_pk_2
        unique (sid)
)
    comment 'The business accounts to which the users belongs to ';

create table questions
(
    id             int auto_increment
        primary key,
    sid            binary(32)                         not null,
    question       longtext                           not null,
    subject_id     int                                not null,
    year_group_id  int                                not null,
    difficulty     enum ('EASY', 'MEDIUM', 'COMPLEX') null,
    category       varchar(100)                       null,
    sub_category   varchar(100)                       null,
    option_1       text                               null,
    option_2       text                               null,
    option_3       text                               null,
    option_4       text                               null,
    correct_option char                               null,
    learn          enum ('LEARN', 'TEST')             null,
    constraint questions_pk_2
        unique (sid)
)
    comment 'This is a temporary table where questions are being stored';

create table subject
(
    id              int auto_increment
        primary key,
    sid             binary(32)                         not null,
    name            varchar(50)                        null,
    description     text                               null,
    created_on      datetime default CURRENT_TIMESTAMP not null,
    last_updated_on datetime                           null,
    constraint subject_pk_2
        unique (sid)
)
    comment 'List of all subjects';

create table year_group
(
    id   int auto_increment comment 'Primary key'
        primary key,
    sid  binary(32) not null comment 'unique string identifier',
    year int        not null comment 'Indicating the year group',
    constraint year_group_pk_2
        unique (sid)
)
    comment 'List of year groups which will be used';

create table test
(
    id                    int auto_increment
        primary key,
    sid                   binary(32)                            not null,
    subject_id            int                                   not null,
    name                  varchar(200)                          null,
    description           text                                  null,
    time_allotted_in_mins int                                   null,
    year_group_id         int                                   not null,
    test_type             enum ('LEARN', 'TEST') default 'TEST' not null,
    enabled               int                    default 1      not null,
    last_updated_on       datetime                              null,
    constraint test_pk_2
        unique (sid),
    constraint test_subject_id_fk
        foreign key (subject_id) references subject (id),
    constraint test_year_group_id_fk
        foreign key (year_group_id) references year_group (id)
)
    comment 'List of all the tests';

create table users
(
    id              int auto_increment comment 'Primary key'
        primary key,
    sid             binary(32)                                    not null comment 'unique binary identifier',
    username        varchar(15)                                   not null comment 'login username for the application',
    password        char(128)                                     null,
    email           varchar(100)                                  null,
    role            enum ('STUDENT', 'TEACHER') default 'STUDENT' not null comment 'defines the role of the user ',
    enabled         int                         default 1         not null comment 'Indicates if the user if active',
    last_login      datetime                                      null comment 'indicates the last login time for the user',
    last_updated_on datetime                                      null,
    related_account int                         default 1         not null,
    year_group_id   int                                           null,
    constraint users_pk_sid
        unique (sid),
    constraint users_accounts_id_fk
        foreign key (related_account) references accounts (id),
    constraint users_year_group_id_fk
        foreign key (year_group_id) references year_group (id)
)
    comment 'All the users using this application are stored here including Students, teachers and admins.';

create table teacher_has_students
(
    teacher_id int null,
    student_id int null,
    constraint teacher_has_students_users_id_fk
        foreign key (teacher_id) references users (id),
    constraint teacher_has_students_users_id_fk_2
        foreign key (student_id) references users (id)
)
    comment 'Many to many mapping of teachers and students';

create table user_tests
(
    id                 int auto_increment
        primary key,
    sid                binary(32)       not null,
    user_id            int              not null,
    test_id            int              not null,
    test_data          json             not null comment 'stores all the test data i.e. ques, answers from the ai model in a JSON',
    start_time         datetime         not null,
    end_time           datetime         null,
    test_score         double default 0 not null,
    ai_recommendations json             null comment 'store all the recommendations in JSON',
    constraint user_tests_pk_2
        unique (sid),
    constraint user_tests_test_id_fk
        foreign key (test_id) references test (id),
    constraint user_tests_users_id_fk
        foreign key (user_id) references users (id)
)
    comment 'Stores all the information about the user test data ';

alter table improvement add column created_on datetime;

create table exam_score
(
    id                 int auto_increment primary key,
    sid                binary(32)       not null,
    user_id            int              not null,
    exam_id            varchar(32)          not null,
    exam_score         json             not null comment 'stores all the exam score data i.e. ques, answers from the ai model in a JSON',
    created_on         datetime         not null,
    constraint exam_score_pk_2
        unique (sid),
    constraint exam_score_user_id_fk
        foreign key (user_id) references users (id)
)
    comment 'Stores all the information about the exam score data ';

alter table user_answers add column exam_score_id int;
alter table questions add column question_ai_id varchar(32);
alter table questions add unique(question_ai_id);
ALTER TABLE user_answers ADD FOREIGN KEY (exam_score_id) REFERENCES exam_score(id);

