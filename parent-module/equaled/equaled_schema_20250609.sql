--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: user_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.user_role AS ENUM (
    'STUDENT',
    'TEACHER',
    'PARENT',
    'ADMIN'
);


ALTER TYPE public.user_role OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id integer NOT NULL,
    sid bytea NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    enabled integer DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_on timestamp without time zone
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accounts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accounts_id_seq OWNER TO postgres;

--
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.id;


--
-- Name: dashboard; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard (
    id integer NOT NULL,
    sid bytea NOT NULL,
    user_id integer NOT NULL,
    subject_id integer NOT NULL,
    exam_id character varying(40),
    start_time timestamp without time zone,
    title text
);


ALTER TABLE public.dashboard OWNER TO postgres;

--
-- Name: dashboard_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dashboard_id_seq OWNER TO postgres;

--
-- Name: dashboard_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_id_seq OWNED BY public.dashboard.id;


--
-- Name: exam_score; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exam_score (
    id integer NOT NULL,
    sid bytea NOT NULL,
    user_id integer NOT NULL,
    exam_id character varying(40) NOT NULL,
    exam_score text NOT NULL,
    created_on timestamp without time zone NOT NULL
);


ALTER TABLE public.exam_score OWNER TO postgres;

--
-- Name: exam_score_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exam_score_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exam_score_id_seq OWNER TO postgres;

--
-- Name: exam_score_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exam_score_id_seq OWNED BY public.exam_score.id;


--
-- Name: frqresponse; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.frqresponse (
    id integer NOT NULL,
    text text,
    user_id integer NOT NULL,
    sid bytea,
    submission_date timestamp without time zone,
    grade character varying(255),
    status character varying(15) DEFAULT 'Pending'::character varying,
    strengths text,
    improvement text,
    section_marks text,
    question_id integer NOT NULL
);


ALTER TABLE public.frqresponse OWNER TO postgres;

--
-- Name: frqresponse_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.frqresponse_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.frqresponse_id_seq OWNER TO postgres;

--
-- Name: frqresponse_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.frqresponse_id_seq OWNED BY public.frqresponse.id;


--
-- Name: frquestions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.frquestions (
    id integer NOT NULL,
    subject_id integer NOT NULL,
    text text,
    difficulty character varying(20) DEFAULT 'Medium'::character varying NOT NULL,
    creation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by integer,
    sid bytea NOT NULL,
    time_limit integer DEFAULT 60 NOT NULL
);


ALTER TABLE public.frquestions OWNER TO postgres;

--
-- Name: frquestions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.frquestions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.frquestions_id_seq OWNER TO postgres;

--
-- Name: frquestions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.frquestions_id_seq OWNED BY public.frquestions.id;


--
-- Name: improvement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.improvement (
    id integer NOT NULL,
    sid bytea NOT NULL,
    user_id integer NOT NULL,
    subject_id integer NOT NULL,
    exam_id character varying(40),
    strong_category text,
    weak_category text,
    score text,
    total_questions integer,
    created_on timestamp without time zone
);


ALTER TABLE public.improvement OWNER TO postgres;

--
-- Name: improvement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.improvement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.improvement_id_seq OWNER TO postgres;

--
-- Name: improvement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.improvement_id_seq OWNED BY public.improvement.id;


--
-- Name: llm_usage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.llm_usage (
    id integer NOT NULL,
    user_id character varying(255) NOT NULL,
    week_start timestamp without time zone NOT NULL,
    week_end timestamp without time zone NOT NULL,
    call_count integer DEFAULT 0,
    is_premium boolean DEFAULT false,
    user_type character varying(50) DEFAULT 'free'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.llm_usage OWNER TO postgres;

--
-- Name: llm_usage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.llm_usage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.llm_usage_id_seq OWNER TO postgres;

--
-- Name: llm_usage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.llm_usage_id_seq OWNED BY public.llm_usage.id;


--
-- Name: passage_answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passage_answers (
    id integer NOT NULL,
    user_answer text,
    user_id integer NOT NULL,
    correct_option character varying(255),
    user_option text,
    user_explanation text,
    explanation text,
    answer_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    status character varying(255),
    passage_question_id integer NOT NULL,
    sid bytea NOT NULL,
    user_exam_id character varying(64) NOT NULL,
    score integer,
    grade integer DEFAULT 0
);


ALTER TABLE public.passage_answers OWNER TO postgres;

--
-- Name: passage_answers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passage_answers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.passage_answers_id_seq OWNER TO postgres;

--
-- Name: passage_answers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passage_answers_id_seq OWNED BY public.passage_answers.id;


--
-- Name: passage_questions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passage_questions (
    id integer NOT NULL,
    passage_id integer NOT NULL,
    text text,
    option_1_text character varying(255),
    option_2_text character varying(255),
    option_3_text character varying(255),
    option_4_text character varying(255),
    option_5_text character varying(255),
    correct_option character varying(255),
    explanation text,
    sid bytea NOT NULL,
    score integer,
    difficulty character varying(15)
);


ALTER TABLE public.passage_questions OWNER TO postgres;

--
-- Name: passage_questions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passage_questions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.passage_questions_id_seq OWNER TO postgres;

--
-- Name: passage_questions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passage_questions_id_seq OWNED BY public.passage_questions.id;


--
-- Name: passages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passages (
    id integer NOT NULL,
    content text,
    title text,
    author integer,
    publication_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    sid bytea NOT NULL
);


ALTER TABLE public.passages OWNER TO postgres;

--
-- Name: passages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.passages_id_seq OWNER TO postgres;

--
-- Name: passages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passages_id_seq OWNED BY public.passages.id;


--
-- Name: practice_user_answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.practice_user_answers (
    id integer NOT NULL,
    sid bytea NOT NULL,
    question_id integer NOT NULL,
    correct_option text NOT NULL,
    user_option text NOT NULL,
    explanation text,
    answer_date timestamp without time zone,
    exam_id character varying(40),
    user_id integer NOT NULL,
    time_spent integer
);


ALTER TABLE public.practice_user_answers OWNER TO postgres;

--
-- Name: practice_user_answers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.practice_user_answers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.practice_user_answers_id_seq OWNER TO postgres;

--
-- Name: practice_user_answers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.practice_user_answers_id_seq OWNED BY public.practice_user_answers.id;


--
-- Name: questions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.questions (
    id integer NOT NULL,
    sid bytea NOT NULL,
    question text NOT NULL,
    subject_id integer NOT NULL,
    year_group_id integer NOT NULL,
    difficulty character varying(10),
    category character varying(100),
    sub_category text,
    option_1 text,
    option_2 text,
    option_3 text,
    option_4 text,
    correct_option text,
    learn character varying(10),
    image_path text,
    user_id integer,
    question_ai_id character varying(256),
    CONSTRAINT questions_difficulty_check CHECK (((difficulty)::text = ANY ((ARRAY['EASY'::character varying, 'MEDIUM'::character varying, 'COMPLEX'::character varying])::text[]))),
    CONSTRAINT questions_learn_check CHECK (((learn)::text = ANY ((ARRAY['LEARN'::character varying, 'TEST'::character varying])::text[])))
);


ALTER TABLE public.questions OWNER TO postgres;

--
-- Name: questions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.questions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.questions_id_seq OWNER TO postgres;

--
-- Name: questions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.questions_id_seq OWNED BY public.questions.id;


--
-- Name: setpractice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.setpractice (
    id integer NOT NULL,
    sid bytea,
    user_id integer NOT NULL,
    practice_name character varying(200),
    time_limit integer,
    questions text,
    no_of_q integer,
    subject_id integer,
    status character varying(20),
    year_group_id integer NOT NULL,
    CONSTRAINT setpractice_status_check CHECK (((status)::text = ANY ((ARRAY['COMPLETED'::character varying, 'PENDING'::character varying])::text[])))
);


ALTER TABLE public.setpractice OWNER TO postgres;

--
-- Name: setpractice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.setpractice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.setpractice_id_seq OWNER TO postgres;

--
-- Name: setpractice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.setpractice_id_seq OWNED BY public.setpractice.id;


--
-- Name: subject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subject (
    id integer NOT NULL,
    sid bytea NOT NULL,
    name character varying(50),
    description text,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_on timestamp without time zone
);


ALTER TABLE public.subject OWNER TO postgres;

--
-- Name: subject_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subject_categories (
    id integer NOT NULL,
    sid bytea,
    subject_id integer,
    sub_category text,
    sub_category_1 text,
    year_group_id integer,
    video_url text,
    country_id character varying(100),
    sort_order integer,
    notes text,
    language character varying(255),
    state character varying(255),
    curriculum character varying(255)
);


ALTER TABLE public.subject_categories OWNER TO postgres;

--
-- Name: subject_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subject_categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.subject_categories_id_seq OWNER TO postgres;

--
-- Name: subject_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subject_categories_id_seq OWNED BY public.subject_categories.id;


--
-- Name: subject_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subject_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.subject_id_seq OWNER TO postgres;

--
-- Name: subject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subject_id_seq OWNED BY public.subject.id;


--
-- Name: system_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.system_config (
    id integer NOT NULL,
    config_key character varying(255) NOT NULL,
    config_value text NOT NULL,
    data_type character varying(50) DEFAULT 'string'::character varying,
    description text,
    category character varying(100) DEFAULT 'general'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.system_config OWNER TO postgres;

--
-- Name: system_config_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.system_config_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.system_config_id_seq OWNER TO postgres;

--
-- Name: system_config_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.system_config_id_seq OWNED BY public.system_config.id;


--
-- Name: teacher_has_students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher_has_students (
    teacher_id integer,
    student_id integer
);


ALTER TABLE public.teacher_has_students OWNER TO postgres;

--
-- Name: test; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.test (
    id integer NOT NULL,
    sid bytea NOT NULL,
    subject_id integer NOT NULL,
    name character varying(200),
    description text,
    time_allotted_in_mins integer,
    year_group_id integer NOT NULL,
    test_type character varying(10) DEFAULT 'TEST'::character varying NOT NULL,
    enabled integer DEFAULT 1 NOT NULL,
    last_updated_on timestamp without time zone,
    no_of_q integer,
    CONSTRAINT test_test_type_check CHECK (((test_type)::text = ANY ((ARRAY['LEARN'::character varying, 'TEST'::character varying])::text[])))
);


ALTER TABLE public.test OWNER TO postgres;

--
-- Name: test_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.test_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.test_id_seq OWNER TO postgres;

--
-- Name: test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.test_id_seq OWNED BY public.test.id;


--
-- Name: user_answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_answers (
    id integer NOT NULL,
    sid bytea NOT NULL,
    question_id integer NOT NULL,
    subject_id integer,
    correct_option text,
    user_option text NOT NULL,
    explanation text,
    time_spent integer,
    answer_date timestamp without time zone,
    exam_id character varying(40),
    user_id integer NOT NULL,
    exam_score_id integer
);


ALTER TABLE public.user_answers OWNER TO postgres;

--
-- Name: user_answers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_answers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_answers_id_seq OWNER TO postgres;

--
-- Name: user_answers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_answers_id_seq OWNED BY public.user_answers.id;


--
-- Name: user_premium_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_premium_status (
    id integer NOT NULL,
    user_id character varying(255) NOT NULL,
    is_premium boolean DEFAULT false,
    user_type character varying(50) DEFAULT 'free'::character varying,
    premium_start_date timestamp without time zone,
    premium_end_date timestamp without time zone,
    subscription_type character varying(50) DEFAULT 'free'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.user_premium_status OWNER TO postgres;

--
-- Name: user_premium_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_premium_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_premium_status_id_seq OWNER TO postgres;

--
-- Name: user_premium_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_premium_status_id_seq OWNED BY public.user_premium_status.id;


--
-- Name: user_progress; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_progress (
    id integer NOT NULL,
    user_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    category character varying(255) NOT NULL,
    completed boolean,
    completed_at timestamp without time zone,
    score integer,
    total_questions integer,
    percentage integer
);


ALTER TABLE public.user_progress OWNER TO postgres;

--
-- Name: user_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_progress_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_progress_id_seq OWNER TO postgres;

--
-- Name: user_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_progress_id_seq OWNED BY public.user_progress.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(100) NOT NULL,
    password character(128),
    email character varying(100),
    role character varying NOT NULL,
    enabled integer DEFAULT 1 NOT NULL,
    last_login timestamp without time zone,
    last_updated_on timestamp without time zone,
    related_account integer DEFAULT 1 NOT NULL,
    year_group_id integer,
    sid bytea NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    dob date,
    country_code character varying(100),
    state_code character varying(100),
    school_name character varying(255),
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['STUDENT'::character varying, 'TEACHER'::character varying, 'PARENT'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: year_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.year_group (
    id integer NOT NULL,
    sid bytea NOT NULL,
    year integer NOT NULL
);


ALTER TABLE public.year_group OWNER TO postgres;

--
-- Name: weekly_user_difficulties_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.weekly_user_difficulties_view AS
 SELECT ua.user_id,
    q.difficulty,
    yg.year AS year_group,
    ua.answer_date
   FROM ((public.user_answers ua
     JOIN public.questions q ON ((ua.question_id = q.id)))
     JOIN public.year_group yg ON ((q.year_group_id = yg.id)));


ALTER VIEW public.weekly_user_difficulties_view OWNER TO postgres;

--
-- Name: weekly_user_submissions_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.weekly_user_submissions_view AS
 SELECT ua.user_id,
    q.difficulty,
    ua.correct_option,
    ua.user_option,
    ua.exam_id,
    yg.year AS year_group,
    ua.answer_date
   FROM ((public.user_answers ua
     JOIN public.questions q ON ((ua.question_id = q.id)))
     JOIN public.year_group yg ON ((q.year_group_id = yg.id)));


ALTER VIEW public.weekly_user_submissions_view OWNER TO postgres;

--
-- Name: year_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.year_group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.year_group_id_seq OWNER TO postgres;

--
-- Name: year_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.year_group_id_seq OWNED BY public.year_group.id;


--
-- Name: accounts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts ALTER COLUMN id SET DEFAULT nextval('public.accounts_id_seq'::regclass);


--
-- Name: dashboard id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard ALTER COLUMN id SET DEFAULT nextval('public.dashboard_id_seq'::regclass);


--
-- Name: exam_score id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_score ALTER COLUMN id SET DEFAULT nextval('public.exam_score_id_seq'::regclass);


--
-- Name: frqresponse id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frqresponse ALTER COLUMN id SET DEFAULT nextval('public.frqresponse_id_seq'::regclass);


--
-- Name: frquestions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frquestions ALTER COLUMN id SET DEFAULT nextval('public.frquestions_id_seq'::regclass);


--
-- Name: improvement id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.improvement ALTER COLUMN id SET DEFAULT nextval('public.improvement_id_seq'::regclass);


--
-- Name: llm_usage id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.llm_usage ALTER COLUMN id SET DEFAULT nextval('public.llm_usage_id_seq'::regclass);


--
-- Name: passage_answers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_answers ALTER COLUMN id SET DEFAULT nextval('public.passage_answers_id_seq'::regclass);


--
-- Name: passage_questions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_questions ALTER COLUMN id SET DEFAULT nextval('public.passage_questions_id_seq'::regclass);


--
-- Name: passages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passages ALTER COLUMN id SET DEFAULT nextval('public.passages_id_seq'::regclass);


--
-- Name: practice_user_answers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.practice_user_answers ALTER COLUMN id SET DEFAULT nextval('public.practice_user_answers_id_seq'::regclass);


--
-- Name: questions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions ALTER COLUMN id SET DEFAULT nextval('public.questions_id_seq'::regclass);


--
-- Name: setpractice id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice ALTER COLUMN id SET DEFAULT nextval('public.setpractice_id_seq'::regclass);


--
-- Name: subject id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject ALTER COLUMN id SET DEFAULT nextval('public.subject_id_seq'::regclass);


--
-- Name: subject_categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject_categories ALTER COLUMN id SET DEFAULT nextval('public.subject_categories_id_seq'::regclass);


--
-- Name: system_config id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_config ALTER COLUMN id SET DEFAULT nextval('public.system_config_id_seq'::regclass);


--
-- Name: test id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test ALTER COLUMN id SET DEFAULT nextval('public.test_id_seq'::regclass);


--
-- Name: user_answers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers ALTER COLUMN id SET DEFAULT nextval('public.user_answers_id_seq'::regclass);


--
-- Name: user_premium_status id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_premium_status ALTER COLUMN id SET DEFAULT nextval('public.user_premium_status_id_seq'::regclass);


--
-- Name: user_progress id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_progress ALTER COLUMN id SET DEFAULT nextval('public.user_progress_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: year_group id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.year_group ALTER COLUMN id SET DEFAULT nextval('public.year_group_id_seq'::regclass);


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- Name: accounts accounts_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_sid_key UNIQUE (sid);


--
-- Name: dashboard dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (id);


--
-- Name: dashboard dashboard_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard
    ADD CONSTRAINT dashboard_sid_key UNIQUE (sid);


--
-- Name: exam_score exam_score_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_score
    ADD CONSTRAINT exam_score_pkey PRIMARY KEY (id);


--
-- Name: exam_score exam_score_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_score
    ADD CONSTRAINT exam_score_sid_key UNIQUE (sid);


--
-- Name: frqresponse frqresponse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frqresponse
    ADD CONSTRAINT frqresponse_pkey PRIMARY KEY (id);


--
-- Name: frqresponse frqresponse_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frqresponse
    ADD CONSTRAINT frqresponse_sid_key UNIQUE (sid);


--
-- Name: frquestions frquestions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frquestions
    ADD CONSTRAINT frquestions_pkey PRIMARY KEY (id);


--
-- Name: improvement improvement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.improvement
    ADD CONSTRAINT improvement_pkey PRIMARY KEY (id);


--
-- Name: improvement improvement_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.improvement
    ADD CONSTRAINT improvement_sid_key UNIQUE (sid);


--
-- Name: llm_usage llm_usage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.llm_usage
    ADD CONSTRAINT llm_usage_pkey PRIMARY KEY (id);


--
-- Name: llm_usage llm_usage_user_week_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.llm_usage
    ADD CONSTRAINT llm_usage_user_week_key UNIQUE (user_id, week_start, week_end);


--
-- Name: passage_answers passage_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_answers
    ADD CONSTRAINT passage_answers_pkey PRIMARY KEY (id);


--
-- Name: passage_answers passage_answers_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_answers
    ADD CONSTRAINT passage_answers_sid_key UNIQUE (sid);


--
-- Name: passage_questions passage_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_questions
    ADD CONSTRAINT passage_questions_pkey PRIMARY KEY (id);


--
-- Name: passage_questions passage_questions_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_questions
    ADD CONSTRAINT passage_questions_sid_key UNIQUE (sid);


--
-- Name: passages passages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passages
    ADD CONSTRAINT passages_pkey PRIMARY KEY (id);


--
-- Name: passages passages_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passages
    ADD CONSTRAINT passages_sid_key UNIQUE (sid);


--
-- Name: practice_user_answers practice_user_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.practice_user_answers
    ADD CONSTRAINT practice_user_answers_pkey PRIMARY KEY (id);


--
-- Name: practice_user_answers practice_user_answers_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.practice_user_answers
    ADD CONSTRAINT practice_user_answers_sid_key UNIQUE (sid);


--
-- Name: questions questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- Name: questions questions_question_ai_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_question_ai_id_key UNIQUE (question_ai_id);


--
-- Name: questions questions_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_sid_key UNIQUE (sid);


--
-- Name: setpractice setpractice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice
    ADD CONSTRAINT setpractice_pkey PRIMARY KEY (id);


--
-- Name: setpractice setpractice_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice
    ADD CONSTRAINT setpractice_sid_key UNIQUE (sid);


--
-- Name: subject_categories subject_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject_categories
    ADD CONSTRAINT subject_categories_pkey PRIMARY KEY (id);


--
-- Name: subject_categories subject_categories_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject_categories
    ADD CONSTRAINT subject_categories_sid_key UNIQUE (sid);


--
-- Name: subject subject_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pkey PRIMARY KEY (id);


--
-- Name: subject subject_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_sid_key UNIQUE (sid);


--
-- Name: system_config system_config_config_key_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_config
    ADD CONSTRAINT system_config_config_key_key UNIQUE (config_key);


--
-- Name: system_config system_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_config
    ADD CONSTRAINT system_config_pkey PRIMARY KEY (id);


--
-- Name: test test_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


--
-- Name: test test_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_sid_key UNIQUE (sid);


--
-- Name: user_answers user_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT user_answers_pkey PRIMARY KEY (id);


--
-- Name: user_answers user_answers_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT user_answers_sid_key UNIQUE (sid);


--
-- Name: user_premium_status user_premium_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_premium_status
    ADD CONSTRAINT user_premium_status_pkey PRIMARY KEY (id);


--
-- Name: user_premium_status user_premium_status_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_premium_status
    ADD CONSTRAINT user_premium_status_user_id_key UNIQUE (user_id);


--
-- Name: user_progress user_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_progress
    ADD CONSTRAINT user_progress_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: year_group year_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.year_group
    ADD CONSTRAINT year_group_pkey PRIMARY KEY (id);


--
-- Name: year_group year_group_sid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.year_group
    ADD CONSTRAINT year_group_sid_key UNIQUE (sid);


--
-- Name: idx_questions_year_group_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_questions_year_group_id ON public.questions USING btree (year_group_id);


--
-- Name: idx_user_answers_answer_date; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_answers_answer_date ON public.user_answers USING btree (answer_date);


--
-- Name: idx_user_answers_question_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_user_answers_question_id ON public.user_answers USING btree (question_id);


--
-- Name: idx_year_group_year; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_year_group_year ON public.year_group USING btree (year);


--
-- Name: practice_user_answers fk1l5qlit2e5cddadxsm1575v0x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.practice_user_answers
    ADD CONSTRAINT fk1l5qlit2e5cddadxsm1575v0x FOREIGN KEY (question_id) REFERENCES public.questions(id);


--
-- Name: setpractice fk22wma5ulrqg6vnbon88isojdw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice
    ADD CONSTRAINT fk22wma5ulrqg6vnbon88isojdw FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: users fk23qwsjpdpj58tsgkj5or4bbh4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk23qwsjpdpj58tsgkj5or4bbh4 FOREIGN KEY (related_account) REFERENCES public.accounts(id);


--
-- Name: subject_categories fk2tf25b8dbb6qvbg4mrafvh6qo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject_categories
    ADD CONSTRAINT fk2tf25b8dbb6qvbg4mrafvh6qo FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: frquestions fk46htkkdyft4kkx2pr0fim3tsj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frquestions
    ADD CONSTRAINT fk46htkkdyft4kkx2pr0fim3tsj FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: frqresponse fk4783k5y1oj4f1cxtucj4d6vaa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frqresponse
    ADD CONSTRAINT fk4783k5y1oj4f1cxtucj4d6vaa FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: frqresponse fk4e0c4nuju45je76aqbjjc4d6f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frqresponse
    ADD CONSTRAINT fk4e0c4nuju45je76aqbjjc4d6f FOREIGN KEY (question_id) REFERENCES public.frquestions(id);


--
-- Name: improvement fk4utb29txu90ve9ro5e51wiod4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.improvement
    ADD CONSTRAINT fk4utb29txu90ve9ro5e51wiod4 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: exam_score fk58a6hqygbvwp15e9rie6l282i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_score
    ADD CONSTRAINT fk58a6hqygbvwp15e9rie6l282i FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: passage_answers fk5nqfi511lcv4unbu1ypw8nxys; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_answers
    ADD CONSTRAINT fk5nqfi511lcv4unbu1ypw8nxys FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: passage_answers fk5pke55r74y6xvtbnhsd8t4jnc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_answers
    ADD CONSTRAINT fk5pke55r74y6xvtbnhsd8t4jnc FOREIGN KEY (passage_question_id) REFERENCES public.passage_questions(id);


--
-- Name: improvement fk65r76j3vok3bdqyoxh6c03u9x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.improvement
    ADD CONSTRAINT fk65r76j3vok3bdqyoxh6c03u9x FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: questions fk68cecpdslb7r14fs8u196k6h4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT fk68cecpdslb7r14fs8u196k6h4 FOREIGN KEY (year_group_id) REFERENCES public.year_group(id);


--
-- Name: user_answers fk6b46l4bb7a6wfxvmn6l7ig8vo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT fk6b46l4bb7a6wfxvmn6l7ig8vo FOREIGN KEY (question_id) REFERENCES public.questions(id);


--
-- Name: teacher_has_students fk6g328wulac5w0k2p61g5k7xu1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_has_students
    ADD CONSTRAINT fk6g328wulac5w0k2p61g5k7xu1 FOREIGN KEY (student_id) REFERENCES public.users(id);


--
-- Name: setpractice fk6g57u5e3h9d3v9pw9lay2qu78; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice
    ADD CONSTRAINT fk6g57u5e3h9d3v9pw9lay2qu78 FOREIGN KEY (year_group_id) REFERENCES public.year_group(id);


--
-- Name: setpractice fk8fqjb5rj2eoqtkvua1h4ab8hj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setpractice
    ADD CONSTRAINT fk8fqjb5rj2eoqtkvua1h4ab8hj FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_progress fk_user_progress_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_progress
    ADD CONSTRAINT fk_user_progress_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: practice_user_answers fkdd3vurk2f7hp0nutq99ngkij0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.practice_user_answers
    ADD CONSTRAINT fkdd3vurk2f7hp0nutq99ngkij0 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: passages fkdt7ltwaq6fmqrn2hjwn07xnjq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passages
    ADD CONSTRAINT fkdt7ltwaq6fmqrn2hjwn07xnjq FOREIGN KEY (author) REFERENCES public.users(id);


--
-- Name: passage_questions fkfowvby0v0kwt8cgvnyvv0xp4h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passage_questions
    ADD CONSTRAINT fkfowvby0v0kwt8cgvnyvv0xp4h FOREIGN KEY (passage_id) REFERENCES public.passages(id);


--
-- Name: teacher_has_students fkgnj0l0q486ux9qi9qpphqpkf9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_has_students
    ADD CONSTRAINT fkgnj0l0q486ux9qi9qpphqpkf9 FOREIGN KEY (teacher_id) REFERENCES public.users(id);


--
-- Name: frquestions fkih7ym20wcxdbsccvvve8311cm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frquestions
    ADD CONSTRAINT fkih7ym20wcxdbsccvvve8311cm FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: questions fkjoo8hp6d3gfwctr68dl2iaemj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT fkjoo8hp6d3gfwctr68dl2iaemj FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_answers fkk4u357ronsopa0vqf16deuxbt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT fkk4u357ronsopa0vqf16deuxbt FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: dashboard fklf74g8h6pjvwa46rxx1ogru0e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard
    ADD CONSTRAINT fklf74g8h6pjvwa46rxx1ogru0e FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: dashboard fklusqdmhtffg9gl024rqtguufb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard
    ADD CONSTRAINT fklusqdmhtffg9gl024rqtguufb FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_answers fklvvqnxdimpaddoc2i3cg1f66g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT fklvvqnxdimpaddoc2i3cg1f66g FOREIGN KEY (exam_score_id) REFERENCES public.exam_score(id);


--
-- Name: questions fko206dvvrqo8k1ujx32g1os6q3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT fko206dvvrqo8k1ujx32g1os6q3 FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: test fkrexpoyol6jlce2a6rfwtg3er8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT fkrexpoyol6jlce2a6rfwtg3er8 FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: user_answers fksy6yar4cpky9vtp3jte5bel94; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_answers
    ADD CONSTRAINT fksy6yar4cpky9vtp3jte5bel94 FOREIGN KEY (subject_id) REFERENCES public.subject(id);


--
-- Name: test fktaq3jbejd0uqs9c86jlw7xt14; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT fktaq3jbejd0uqs9c86jlw7xt14 FOREIGN KEY (year_group_id) REFERENCES public.year_group(id);


--
-- Name: users fktdgo09l09fqpv6als4hn4xidx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fktdgo09l09fqpv6als4hn4xidx FOREIGN KEY (year_group_id) REFERENCES public.year_group(id);


--
-- Name: subject_categories fktgy36x4jxjci9g0v1uiivcyt7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject_categories
    ADD CONSTRAINT fktgy36x4jxjci9g0v1uiivcyt7 FOREIGN KEY (year_group_id) REFERENCES public.year_group(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT USAGE ON SCHEMA public TO equaled;


--
-- Name: TABLE accounts; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.accounts TO equaled;


--
-- Name: TABLE dashboard; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.dashboard TO equaled;


--
-- Name: TABLE exam_score; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.exam_score TO equaled;


--
-- Name: TABLE frqresponse; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.frqresponse TO equaled;


--
-- Name: TABLE frquestions; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.frquestions TO equaled;


--
-- Name: TABLE improvement; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.improvement TO equaled;


--
-- Name: TABLE llm_usage; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.llm_usage TO equaled;


--
-- Name: TABLE passage_answers; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.passage_answers TO equaled;


--
-- Name: TABLE passage_questions; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.passage_questions TO equaled;


--
-- Name: TABLE passages; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.passages TO equaled;


--
-- Name: TABLE practice_user_answers; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.practice_user_answers TO equaled;


--
-- Name: TABLE questions; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.questions TO equaled;


--
-- Name: TABLE setpractice; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.setpractice TO equaled;


--
-- Name: TABLE subject; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.subject TO equaled;


--
-- Name: TABLE subject_categories; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.subject_categories TO equaled;


--
-- Name: TABLE system_config; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.system_config TO equaled;


--
-- Name: TABLE teacher_has_students; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.teacher_has_students TO equaled;


--
-- Name: TABLE test; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.test TO equaled;


--
-- Name: TABLE user_answers; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.user_answers TO equaled;


--
-- Name: TABLE user_premium_status; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.user_premium_status TO equaled;


--
-- Name: TABLE user_progress; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.user_progress TO equaled;


--
-- Name: TABLE users; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.users TO equaled;


--
-- Name: TABLE year_group; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.year_group TO equaled;


--
-- Name: TABLE weekly_user_difficulties_view; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.weekly_user_difficulties_view TO equaled;


--
-- Name: TABLE weekly_user_submissions_view; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.weekly_user_submissions_view TO equaled;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO equaled;


--
-- PostgreSQL database dump complete
--

