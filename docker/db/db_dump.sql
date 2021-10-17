--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 14.0

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: compose-postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO "compose-postgres";

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: compose-postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: authorship; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.authorship (
    id integer NOT NULL,
    book_id integer NOT NULL,
    person_id integer NOT NULL
);


ALTER TABLE public.authorship OWNER TO "compose-postgres";

--
-- Name: authorship_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.authorship_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorship_id_seq OWNER TO "compose-postgres";

--
-- Name: authorship_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.authorship_id_seq OWNED BY public.authorship.id;


--
-- Name: blocking; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.blocking (
    id integer NOT NULL,
    book_id integer NOT NULL,
    person_id integer NOT NULL,
    date_from date,
    date_to date,
    is_borrowed boolean
);


ALTER TABLE public.blocking OWNER TO "compose-postgres";

--
-- Name: blocking_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.blocking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.blocking_id_seq OWNER TO "compose-postgres";

--
-- Name: blocking_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.blocking_id_seq OWNED BY public.blocking.id;


--
-- Name: book; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.book (
    id integer NOT NULL,
    library_id integer NOT NULL,
    name character varying(50),
    release date,
    isbn character(13),
    publisher character varying(50),
    genre character varying(30),
    rate smallint
);


ALTER TABLE public.book OWNER TO "compose-postgres";

--
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_id_seq OWNER TO "compose-postgres";

--
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO "compose-postgres";

--
-- Name: library; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.library (
    id integer NOT NULL,
    name character varying(50),
    tag character(5),
    street character varying(255),
    city character varying(50),
    street_number character varying(20),
    from_time time without time zone,
    to_time time without time zone,
    description character varying(500)
);


ALTER TABLE public.library OWNER TO "compose-postgres";

--
-- Name: library_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.library_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.library_id_seq OWNER TO "compose-postgres";

--
-- Name: library_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.library_id_seq OWNED BY public.library.id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.person (
    id integer NOT NULL,
    name character varying(20),
    surname character varying(50),
    birth_date date,
    role character varying(20),
    username character varying(50),
    password character varying(255),
    library_id integer
);


ALTER TABLE public.person OWNER TO "compose-postgres";

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO "compose-postgres";

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- Name: authorship id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship ALTER COLUMN id SET DEFAULT nextval('public.authorship_id_seq'::regclass);


--
-- Name: blocking id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking ALTER COLUMN id SET DEFAULT nextval('public.blocking_id_seq'::regclass);


--
-- Name: book id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- Name: library id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.library ALTER COLUMN id SET DEFAULT nextval('public.library_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Data for Name: authorship; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.authorship (id, book_id, person_id) FROM stdin;
\.


--
-- Data for Name: blocking; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.blocking (id, book_id, person_id, date_from, date_to, is_borrowed) FROM stdin;
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.book (id, library_id, name, release, isbn, publisher, genre, rate) FROM stdin;
1	1	Mikirova uzasna pout	2017-03-14	ABC15        	Korbi	vesmirna komedie	5
\.


--
-- Data for Name: library; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.library (id, name, tag, street, city, street_number, from_time, to_time, description) FROM stdin;
1	knihovnos	k1   	Plumlovska	Prostejov	127	08:00:00	18:00:00	perfektni knihovna kamo
2	kasdfa	k1   	Plumlovska	Prostejov	127	08:00:00	18:00:00	perfektni knihovna kamo
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.person (id, name, surname, birth_date, role, username, password, library_id) FROM stdin;
1	Tomas	Korbar	1970-01-01	ADMIN	korbonaut	$2a$10$GRQmNECbcOX.e1IvtwgnOelX6O4VVS7sXxfYsySOeqN/Gy6ZZ5hNG	\N
2	Tomas1	Korbar2	1970-01-01	LIBRARIAN	libr	$2a$10$GRQmNECbcOX.e1IvtwgnOelX6O4VVS7sXxfYsySOeqN/Gy6ZZ5hNG	1
3	Tomas1	Korbar2	1970-01-01	LIBRARIAN	libr2	$2a$10$GRQmNECbcOX.e1IvtwgnOelX6O4VVS7sXxfYsySOeqN/Gy6ZZ5hNG	1
\.


--
-- Name: authorship_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.authorship_id_seq', 1, false);


--
-- Name: blocking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.blocking_id_seq', 1, false);


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.book_id_seq', 1, false);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, true);


--
-- Name: library_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.library_id_seq', 1, false);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 1, true);


--
-- Name: authorship authorship_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT authorship_pkey PRIMARY KEY (id);


--
-- Name: blocking blocking_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT blocking_pkey PRIMARY KEY (id);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: library library_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.library
    ADD CONSTRAINT library_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: person_username_uindex; Type: INDEX; Schema: public; Owner: compose-postgres
--

CREATE UNIQUE INDEX person_username_uindex ON public.person USING btree (username);


--
-- Name: blocking book; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: authorship book; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: book library; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);


--
-- Name: blocking person; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT person FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: authorship person; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT person FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: person person_library_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_library_id_fk FOREIGN KEY (library_id) REFERENCES public.library(id);


--
-- PostgreSQL database dump complete
--

