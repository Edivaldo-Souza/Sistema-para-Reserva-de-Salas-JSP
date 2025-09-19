CREATE TABLE tb_request(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    room_id BIGINT,
    start_date TIMESTAMP,
    duration NUMERIC,
    status VARCHAR(50),

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES tb_room(id)
)