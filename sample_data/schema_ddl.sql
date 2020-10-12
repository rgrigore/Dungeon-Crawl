DROP TABLE IF EXISTS public.game_state;
DROP TABLE IF EXISTS public.inventory;
DROP TABLE IF EXISTS public.items;
DROP TABLE IF EXISTS public.player;

CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    current_map text NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

CREATE TABLE public.items (
    id serial NOT NULL,
    item character varying NOT NULL,
    PRIMARY KEY (id)
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

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

CREATE TABLE public.inventory (
    id serial NOT NULL,
    player_id integer NOT NULL,
    item_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_player_id FOREIGN KEY (player_id)
        REFERENCES public.player (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_item_id FOREIGN KEY (item_id)
        REFERENCES public.items (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO items (item)
VALUES ('Sword'),
       ('Armour'),
       ('Yellow Key'),
       ('Red Key'),
       ('Blue Key');
