CREATE TABLE tb_room(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    price DOUBLE PRECISION,
    capacity INT,
    sector_id BIGINT,

    CONSTRAINT fk_sector FOREIGN KEY (sector_id) REFERENCES tb_sector(id)
)