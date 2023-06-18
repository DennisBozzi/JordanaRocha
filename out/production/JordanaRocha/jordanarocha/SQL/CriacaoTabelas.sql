/*Criando tabela Vendedor*/
CREATE TABLE `vendedor` (
  `id_vendedor` int NOT NULL AUTO_INCREMENT,
  `comissao_vendedor` double DEFAULT NULL,
  `nome_vendedor` varchar(255) DEFAULT NULL,
  `cpf_vendedor` varchar(20) DEFAULT NULL,
  `rg_vendedor` varchar(20) DEFAULT NULL,
  `endereco_vendedor` varchar(255) DEFAULT NULL,
  `celular_vendedor` varchar(20) DEFAULT NULL,
  `foto_vendedor` longblob,
  `email_vendedor` varchar(255) DEFAULT NULL,
  `usuario_vendedor` varchar(255) DEFAULT NULL,
  `data_demissao` date DEFAULT NULL,
  `data_admissao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `senha_vendedor` varchar(255) DEFAULT NULL,
  `observacao` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`id_vendedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Criando tabela Cliente*/
CREATE TABLE clientes (
  id_cliente INT AUTO_INCREMENT,
  nome_cliente VARCHAR(255),
  cpf_cliente VARCHAR(20),
  endereco_cliente VARCHAR(255),
  celular_cliente VARCHAR(20),
  email_cliente VARCHAR(255),
  observacao_cliente TEXT,
  PRIMARY KEY (id_cliente)
);

/*Criando tabela Atributos*/
CREATE TABLE atributos (
  id_atributo INT AUTO_INCREMENT,
  nome_atributo VARCHAR(255),
  valor_atributo VARCHAR(255),
  PRIMARY KEY (id_atributo)
);

