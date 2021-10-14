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
-- Name: blocking; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.blocking (
    id integer NOT NULL,
    book_id integer NOT NULL,
    library_id integer NOT NULL,
    person_id integer NOT NULL,
    date_from date,
    date_to date,
    is_borrowed boolean
);


ALTER TABLE public.blocking OWNER TO "compose-postgres";

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
-- Name: person; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.person (
    id integer NOT NULL,
    name character varying(20),
    surname character varying(50),
    birth_date date,
    role smallint,
    username character varying(50),
    password character varying(255)
);


ALTER TABLE public.person OWNER TO "compose-postgres";

--
-- Data for Name: authorship; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.authorship (id, book_id, person_id) FROM stdin;
1	1	1
2	2	1
3	3	2
4	1	2
\.


--
-- Data for Name: blocking; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.blocking (id, book_id, library_id, person_id, date_from, date_to, is_borrowed) FROM stdin;
1	1	1	1	2021-10-07	2021-10-13	t
2	2	2	2	2021-10-07	2021-10-08	t
3	3	1	2	2021-10-10	2021-10-12	f
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.book (id, library_id, name, release, isbn, publisher, genre, rate) FROM stdin;
1	1	AHoj	\N	\N	NY	KOKOT	\N
2	1	Svete	\N	\N	ASD	PICOVINA	\N
3	2	Dneska	\N	\N	DSS	HOROR	\N
\.


--
-- Data for Name: library; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.library (id, name, tag, street, city, street_number, from_time, to_time, description) FROM stdin;
1	MZK	MZK  	\N	\N	\N	\N	\N	\N
2	BKN	BKN  	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.person (id, name, surname, birth_date, role, username, password) FROM stdin;
1	Petr	Kokot	\N	\N	\N	\N
2	Honza	Picas	\N	\N	\N	\N
\.


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
-- Name: blocking library; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);


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
-- PostgreSQL database dump complete
--

