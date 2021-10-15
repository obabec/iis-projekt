--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

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
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.person (id, name, surname, birth_date, role, username, password) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


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

