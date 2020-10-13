DROP TABLE IF EXISTS public.game_state;
DROP TABLE IF EXISTS public.inventory;
DROP TABLE IF EXISTS public.item;
DROP TABLE IF EXISTS public.items;
DROP TABLE IF EXISTS public.player;
DROP TABLE IF EXISTS public.mob;
DROP TABLE IF EXISTS public.mobs;
DROP TABLE IF EXISTS public.map;

CREATE TABLE public.map (
    id serial NOT NULL,
    width integer NOT NULL,
    height integer NOT NULL,
    terrain text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.mobs (
    symbol "char" NOT NULL,
    name character varying(20) NOT NULL UNIQUE,
    PRIMARY KEY (symbol)
);

CREATE TABLE public.mob (
    id serial NOT NULL,
    map_id integer NOT NULL,
    type_symbol "char" NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    hp integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_map_id FOREIGN KEY (map_id)
        REFERENCES public.map (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_type_symbol FOREIGN KEY (type_symbol)
        REFERENCES public.mobs (symbol) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    max_hp integer NOT NULL,
    hp integer NOT NULL,
    attack integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

CREATE TABLE public.items (
    symbol "char" NOT NULL,
    name character varying(20) NOT NULL UNIQUE,
    PRIMARY KEY (symbol)
);

CREATE TABLE public.item (
    id serial NOT NULL,
    type_symbol "char" NOT NULL,
    map_id integer,
    x integer,
    y integer,
    PRIMARY KEY (id),
    CONSTRAINT fk_type_symbol FOREIGN KEY (type_symbol)
        REFERENCES public.items (symbol) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_map_id FOREIGN KEY (map_id)
        REFERENCES public.map (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.inventory (
    id serial NOT NULL,
    player_id integer NOT NULL,
    item_symbol "char" NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_player_id FOREIGN KEY (player_id)
        REFERENCES public.player (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE ,
    CONSTRAINT fk_item_symbol FOREIGN KEY (item_symbol)
        REFERENCES public.items (symbol) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.game_state (
    id serial NOT NULL,
    name character varying(20) NOT NULL,
    saved_at timestamp without time zone NOT NULL,
    player_id integer NOT NULL,
    map_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_player_id FOREIGN KEY (player_id)
        REFERENCES public.player (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_map_id FOREIGN KEY (map_id)
        REFERENCES public.map (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO mobs (symbol, name)
VALUES ('s', 'Skeleton'),
       ('g', 'Ghost');

INSERT INTO items (symbol, name)
VALUES ('|', 'Sword'),
       ('-', 'Armour'),
       ('y', 'Yellow Key'),
       ('r', 'Red Key'),
       ('b', 'Blue Key'),
       ('+', 'Heal Big'),
       ('*', 'Heal Small'),
       ('Y', 'Yellow Door'),
       ('R', 'Red Door'),
       ('B', 'Blue Door'),
       ('^', 'Portal');
