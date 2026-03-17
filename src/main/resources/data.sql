INSERT INTO tipo_usuario (nome_tipo)
SELECT 'Dono de Restaurante'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_usuario
    WHERE nome_tipo = 'Dono de Restaurante'
);

INSERT INTO tipo_usuario (nome_tipo)
SELECT 'Cliente'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_usuario
    WHERE nome_tipo = 'Cliente'
);