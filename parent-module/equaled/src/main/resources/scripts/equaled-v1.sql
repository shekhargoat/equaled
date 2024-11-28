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

create table improvement
(
    id              int primary key auto_increment,
    sid             binary(32)                         null,
    exam_id         varchar(200)                       null,
    strong_category varchar(500)                       null,
    weak_column     varchar(500)                       null,
    score           int                                null,
    total_questions int                                null,
    user_id         int                                not null,
    subject_id      int                                not null,
    created_on      datetime default current_timestamp not null,
    constraint improvement_pk
        unique (sid),
    constraint improvement_user_fk
        foreign key (user_id) references users (id),
    constraint improvement_subject__fk
        foreign key (subject_id) references subject (id)
);

alter table improvement
    change weak_column weak_category varchar(500) null;


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

create table user_answers
(
    id              int auto_increment,
    sid             binary(32)  not null,
    correct_option text        null,
    user_option     text        null,
    explanation     text        null,
    time_spent      int         null,
    answer_date     datetime    null,
    exam_score_id   int         null,
    subject_id      int         null,
    question_id     int         null,
    user_id         int         null,
    exam_id         varchar(32) null,
    constraint user_answers_pk
        primary key (id),
    constraint user_answers_exam_score_fk
        foreign key (exam_score_id) references exam_score (id),
    constraint user_answers_question_fk
        foreign key (question_id) references questions (id),
    constraint user_answers_subject_fk
        foreign key (subject_id) references subject (id)
);

alter table user_answers
    add constraint user_answers_sid_pk
        unique (sid);

alter table user_answers
    add constraint user_answers_user__fk
        foreign key (user_id) references users (id);




alter table questions add column question_ai_id varchar(32);
alter table questions add unique(question_ai_id);

create table dashboard
(
    id         int auto_increment,
    sid        binary(32)                         not null,
    subject_id int                                null,
    exam_id    varchar(200)                       null,
    start_time datetime default current_timestamp null,
    user_id    int                                null,
    title      text                               null,
    constraint dashboard_pk
        primary key (id),
    constraint dashboard_pk_2
        unique (sid),
    constraint dashboard_subject__fk
        foreign key (subject_id) references subject (id),
    constraint dashboard_user__fk
        foreign key (user_id) references users (id)
);

create table practice_user_answers
(
    id              int auto_increment,
    sid             binary(32)  not null,
    correct_option text        null,
    user_option     text        null,
    explanation     text        null,
    time_spent      int         null,
    answer_date     datetime    null,
    question_id     int         null,
    user_id         int         null,
    exam_id         varchar(32) null,
    constraint practice_user_answers_pk
        primary key (id),
    constraint practice_user_answers_question_fk
        foreign key (question_id) references questions (id)
);

alter table practice_user_answers
    add constraint practice_user_answers_sid_pk
        unique (sid);

alter table practice_user_answers
    add constraint practice_user_answers_user__fk
        foreign key (user_id) references users (id);

create table setpractice
(
    id            int auto_increment,
    sid           binary(32)                  null,
    user_id       int                         null,
    practice_name varchar(200)                null,
    time_limit    int                         null,
    no_of_q       int                         null,
    subject_id    int                         null,
    status        ENUM ('ACTIVE', 'INACTIVE') null,
    year_group_id int                         null,
    constraint setpractice_pk
        primary key (id),
    constraint setpractice_pk_2
        unique (sid),
    constraint setpractice_subject_fk
        foreign key (subject_id) references subject (id),
    constraint setpractice_user_fk
        foreign key (user_id) references users (id),
    constraint setpractice_year_group_fk
        foreign key (year_group_id) references year_group (id)
);

alter table setpractice
    modify status enum ('COMPLETED', 'PENDING') null;

create table subject_categories
(
    id             int auto_increment,
    sid            binary(32)   null,
    subject_id     int          null,
    sub_category   varchar(200) null,
    sub_category_1 varchar(200) null,
    year_group_id  int          null,
    constraint subject_categories_pk
        primary key (id),
    constraint subject_categories_pk_2
        unique (sid),
    constraint subject_categories_subject__fk
        foreign key (subject_id) references subject (id),
    constraint subject_categories_year_group_fk
        foreign key (year_group_id) references year_group (id)
);

alter table questions
    add image_path longtext null;


alter table passages
    change PassageID id int auto_increment;

alter table passages
    auto_increment = 1;

alter table passages
    change Content content longtext null;

alter table passages
    change Title title longtext null;

alter table passages
    change Author author int null;

alter table passages
    change PublicationDate publication_date datetime default CURRENT_TIMESTAMP null;

alter table passages
    change Passagequestions passage_questions json null;

alter table passages
    change Passageanswer passage_answer longtext null;

alter table passages
    add sid binary(32) not null;

alter table passages
    add constraint passages_pk
        unique (sid);

rename table passageanswer to passage_answer;
rename table equaled.passagequestions to passage_questions;

alter table passages
    drop column passage_questions;

alter table passages
    drop column passage_answer;

alter table passages
    add constraint passages_user__fk
        foreign key (author) references users (id);

alter table passage_questions
    change QuestionID id int auto_increment;

alter table passage_questions
    change PassageID passage_id int not null;

alter table passage_questions
    change Text text longtext null;

alter table passage_questions
    change Option_1_text option_1_text varchar(255) null;

alter table passage_questions
    change Option_2_text option_2_text varchar(255) null;

alter table passage_questions
    change Option_3_text option_3_text varchar(255) null;

alter table passage_questions
    change Option_4_text option_4_text varchar(255) null;

alter table passage_questions
    change Option_5_text option_5_text varchar(255) null;

alter table passage_questions
    change Explanation explanation longtext null;

alter table passage_questions
    add sid binary(32) not null;

alter table passage_questions
    add constraint passage_questions_unq
        unique (sid);

alter table passage_questions
    add constraint passage_questions_id__fk
        foreign key (passage_id) references passages (id);


alter table passage_answer
    change SeqNo id int auto_increment;

alter table passage_answer
    change User_answer user_answer longtext null;

alter table passage_answer
    change User_id user_id int null;

alter table passage_answer
    drop column Passage;

alter table passage_answer
    drop column Passagetext;

alter table passage_answer
    drop column Text;

alter table passage_answer
    drop column Option_1_text;

alter table passage_answer
    drop column Option_2_text;

alter table passage_answer
    drop column Option_3_text;

alter table passage_answer
    drop column Option_4_text;

alter table passage_answer
    change User_option user_option varchar(255) null;

alter table passage_answer
    change User_explanation user_explanation longtext null;

alter table passage_answer
    change Date date datetime default CURRENT_TIMESTAMP null;

alter table passage_answer
    change Status status varchar(255) null;

alter table passage_answer
    change Difficulty difficulty varchar(255) null;

alter table passage_answer
    change Score score int null;

alter table passage_answer
    add passage_question_id int not null;

alter table passage_answer
    add sid binary(32) not null;

alter table passage_answer
    add constraint passage_answer_pk
        unique (sid);

alter table passage_answer
    add constraint passage_answer_quesiton__fk
        foreign key (passage_question_id) references passage_questions (id);

alter table passage_questions
    add score int null;

alter table passage_questions
    add difficulty varchar(15) null;

rename table passage_answer to passage_answers;

alter table passage_answers
    modify user_id int not null;

alter table passage_answers
    add constraint passage_answers_user__fk
        foreign key (user_id) references users (id);

alter table passage_answers
    add column user_exam_id varchar(64) not null;

alter table passage_answers
    add constraint passage_answers_exam_id
        unique (user_exam_id);

alter table passage_answers
    change date answer_date datetime not null;

alter table passage_answers
    alter column answer_date set default (current_timestamp);

alter table passage_answers
    drop column difficulty;

alter table passage_answers
    drop column score;





