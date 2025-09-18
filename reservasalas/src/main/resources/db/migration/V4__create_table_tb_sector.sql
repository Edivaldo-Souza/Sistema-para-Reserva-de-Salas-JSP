CREATE TABLE tb_sector(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    cash_amount DOUBLE PRECISION,
    user_id BIGINT,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
)