//https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html
INSERT INTO `clube`.`Socio` (`nome`,`senha`,`ativo`, `endereco`, `email`, `telefone`) VALUES ('joca','$2a$10$UVdy8VaOiIbPlrccd7y7weqpQMMW7l21vJRzaQLPz4VpIN.hw3WBm',1,'Rua Almeida Prado, 33', 'jcardoso@gmail.com','2345-2313'),('maria','$2a$10$UVdy8VaOiIbPlrccd7y7weqpQMMW7l21vJRzaQLPz4VpIN.hw3WBm',1,'Av. São Joaquim, 1932','mangela@hotmail.com','93823-3438');
INSERT INTO `clube`.`Autorizacao` (`nome`, `perfil`) VALUES ('joca','ROLE_ADMIN'),('usuario','ROLE_ADMIN');
