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
-- Name: author; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.author (
                               id integer NOT NULL,
                               name character varying(50),
                               surname character varying(100)
);


ALTER TABLE public.author OWNER TO "compose-postgres";

--
-- Name: author_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.author_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.author_id_seq OWNER TO "compose-postgres";

--
-- Name: author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.author_id_seq OWNED BY public.author.id;


--
-- Name: authorship; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.authorship (
                                   id integer NOT NULL,
                                   book_id integer NOT NULL,
                                   author_id integer NOT NULL
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
                             library_id integer,
                             name character varying(50),
                             release date,
                             isbn character varying(13),
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
-- Name: book_order; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.book_order (
                                   id integer NOT NULL,
                                   title_id integer NOT NULL,
                                   library_id integer NOT NULL,
                                   count integer
);


ALTER TABLE public.book_order OWNER TO "compose-postgres";

--
-- Name: book_order_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.book_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_order_id_seq OWNER TO "compose-postgres";

--
-- Name: book_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.book_order_id_seq OWNED BY public.book_order.id;


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
                                tag character varying(5),
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
-- Name: user_vote; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.user_vote (
                                  id integer NOT NULL,
                                  vote_id integer NOT NULL,
                                  user_id integer NOT NULL
);


ALTER TABLE public.user_vote OWNER TO "compose-postgres";

--
-- Name: user_vote_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.user_vote_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_vote_id_seq OWNER TO "compose-postgres";

--
-- Name: user_vote_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.user_vote_id_seq OWNED BY public.user_vote.id;


--
-- Name: votes; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.votes (
                              id integer NOT NULL,
                              book_id integer NOT NULL,
                              library_id integer NOT NULL,
                              vote_amount integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.votes OWNER TO "compose-postgres";

--
-- Name: votes_id_seq; Type: SEQUENCE; Schema: public; Owner: compose-postgres
--

CREATE SEQUENCE public.votes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.votes_id_seq OWNER TO "compose-postgres";

--
-- Name: votes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: compose-postgres
--

ALTER SEQUENCE public.votes_id_seq OWNED BY public.votes.id;


--
-- Name: author id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.author ALTER COLUMN id SET DEFAULT nextval('public.author_id_seq'::regclass);


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
-- Name: book_order id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book_order ALTER COLUMN id SET DEFAULT nextval('public.book_order_id_seq'::regclass);


--
-- Name: library id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.library ALTER COLUMN id SET DEFAULT nextval('public.library_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Name: user_vote id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.user_vote ALTER COLUMN id SET DEFAULT nextval('public.user_vote_id_seq'::regclass);


--
-- Name: votes id; Type: DEFAULT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.votes ALTER COLUMN id SET DEFAULT nextval('public.votes_id_seq'::regclass);


--
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.author (id, name, surname) FROM stdin;
2	George	Orwell
3	Andrzej 	Sapkowski
4	Joanne K.	Rowlingová
\.


--
-- Data for Name: authorship; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.authorship (id, book_id, author_id) FROM stdin;
26	3	2
27	11	3
28	12	3
29	13	4
30	14	4
31	15	4
32	5	2
33	10	3
34	9	3
35	8	3
36	16	4
37	17	4
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
3	2	Farma zvirat	1945-09-17	978-80-257-13	Harvill Secker	Beletrie	10
11	\N	Zaklínač - Čas opovržení	2011-01-01	9788085951707	Leonardo	Fantasy	9
12	\N	Zaklínač - Křest ohněm	2011-01-01	9788085951714	Leonardo	Fantasy	9
13	1	Harry Potter a Kámen mudrců	2000-01-01	80-00-00788-6	Albatros	Fantasy	10
14	1	Harry Potter a Tajemná komnata	2000-01-01	80-00-00898-X	Albatros	Fantasy	9
15	1	Harry Potter a vězeň z Azkabanu	2001-01-01	80-00-00951-X	Albatros	Fantasy	10
5	1	Nineteen Eighty-Four	1949-06-08	80-206-0256-9	XY	Politicka fikce	9
10	2	Zaklínač - Krev elfů	2011-02-01	9788085951691	Leonardo	Fantasy	8
9	2	Zaklínač - Meč osudu 	2011-02-01	978-80-85951-	Leonardo	Fantasy	9
8	2	Zaklínač - Poslední přání	2011-02-01	9788085951653	Leonardo	Fantasy	9
16	\N	Harry Potter a Ohnivý pohár	2001-01-01	80-00-00994-3	Albatros	Fantasy	9
17	\N	Harry Potter a Fénixův řád	2004-01-01	80-00-01294-4	Albatros	Fantasy	10
\.


--
-- Data for Name: book_order; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.book_order (id, title_id, library_id, count) FROM stdin;
\.


--
-- Data for Name: library; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.library (id, name, tag, street, city, street_number, from_time, to_time, description) FROM stdin;
1	NKProst	k1   	Plumlovska	Prostejov	127	08:00:00	18:00:00	Nejstarsi knihovna v Prostejove
2	KABrno	k2   	Olomoucka	Brno	3	09:00:00	21:00:00	Knihovna a technicky archiv Brno
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.person (id, name, surname, birth_date, role, username, password, library_id) FROM stdin;
1	Admin	Admin	1970-01-01	ADMIN	admin	$2a$10$rVaZ2vVT9C9uaTleRgaEmuZ385dd/VRiPiWie2gfu4dO0AwC1rHbe	\N
2	Libr	Libr1	1970-01-01	LIBRARIAN	libr	$2a$10$Fjj/JiTCnyBK1xtjudjfJO/2Es9rJQo5JQx418QDZj8j3dQVIHWSO	1
3	Libr	Libr2	1970-01-01	LIBRARIAN	libr2	$2a$10$YLAv7ZhUmvVsE5fchMYXG.9w04OIishTllRwKZ9sck3X0vhOEs3Wq	2
4	Pepa	Novak	1990-01-01	READER	reader	$2a$10$cH8EGOhWooZ/6Gn/I8nhzOEMe.mTrto9mTbZBbco6iGNZHtka8KJK	1
5	Distrib	Distrib	1970-01-01	DISTRIBUTOR	distributor	$2a$10$VIO/SQEnniUYdDlTjMX4WOE/iZcrHxszN11yerbLErhvdQTbOlwYm	1
\.


--
-- Data for Name: user_vote; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.user_vote (id, vote_id, user_id) FROM stdin;
\.


--
-- Data for Name: votes; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.votes (id, book_id, library_id, vote_amount) FROM stdin;
\.


--
-- Name: author_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.author_id_seq', 4, true);


--
-- Name: authorship_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.authorship_id_seq', 38, true);


--
-- Name: blocking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.blocking_id_seq', 4, true);


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.book_id_seq', 18, true);


--
-- Name: book_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.book_order_id_seq', 1, false);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 5, true);


--
-- Name: library_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.library_id_seq', 2, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 5, true);


--
-- Name: user_vote_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.user_vote_id_seq', 1, false);


--
-- Name: votes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: compose-postgres
--

SELECT pg_catalog.setval('public.votes_id_seq', 46, true);


--
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


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
-- Name: book_order book_order_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book_order
    ADD CONSTRAINT book_order_pkey PRIMARY KEY (id);


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
-- Name: user_vote user_vote_pk; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.user_vote
    ADD CONSTRAINT user_vote_pk PRIMARY KEY (id);


--
-- Name: votes votes_pk; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT votes_pk PRIMARY KEY (id);


--
-- Name: person_username_uindex; Type: INDEX; Schema: public; Owner: compose-postgres
--

CREATE UNIQUE INDEX person_username_uindex ON public.person USING btree (username);


--
-- Name: user_vote_id_uindex; Type: INDEX; Schema: public; Owner: compose-postgres
--

CREATE UNIQUE INDEX user_vote_id_uindex ON public.user_vote USING btree (id);


--
-- Name: votes_id_uindex; Type: INDEX; Schema: public; Owner: compose-postgres
--

CREATE UNIQUE INDEX votes_id_uindex ON public.votes USING btree (id);


--
-- Name: authorship author; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT author FOREIGN KEY (author_id) REFERENCES public.author(id) ON DELETE CASCADE;


--
-- Name: blocking book; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id) ON DELETE CASCADE;


--
-- Name: authorship book; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id) ON DELETE CASCADE;


--
-- Name: votes book_fk; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT book_fk FOREIGN KEY (book_id) REFERENCES public.book(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: book library; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);


--
-- Name: book_order library; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book_order
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);


--
-- Name: votes library_fk; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT library_fk FOREIGN KEY (library_id) REFERENCES public.library(id) ON DELETE CASCADE;


--
-- Name: blocking person; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT person FOREIGN KEY (person_id) REFERENCES public.person(id) ON DELETE CASCADE;


--
-- Name: person person_library_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_library_id_fk FOREIGN KEY (library_id) REFERENCES public.library(id);


--
-- Name: book_order title; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book_order
    ADD CONSTRAINT title FOREIGN KEY (title_id) REFERENCES public.book(id);


--
-- Name: user_vote user_vote_user_id; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.user_vote
    ADD CONSTRAINT user_vote_user_id FOREIGN KEY (user_id) REFERENCES public.person(id) ON DELETE CASCADE;


--
-- Name: user_vote user_vote_vote_id; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.user_vote
    ADD CONSTRAINT user_vote_vote_id FOREIGN KEY (vote_id) REFERENCES public.votes(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

