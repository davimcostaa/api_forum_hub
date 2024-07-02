CREATE TABLE respostas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    solucao BOOLEAN NOT NULL,
    usuario_id BIGINT,
    topico_id BIGINT,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topico FOREIGN KEY (topico_id) REFERENCES topicos(id)
);
