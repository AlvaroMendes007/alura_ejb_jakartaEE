# EJB com Jakarta EE

Foi utilizado EJB com Jakarta EE para desenvolver uma aplicação de envio de email automático com a anotação @Schedule para programar envio a cada 10 segundos

### Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- JMS -> armazena as transações em uma fila e retorna ao usuário os emails não enviados
- Singleton -> mantém uma instância do objeto para que não haja problema
- JPA -> faz os comandos do banco de dados
- Maven -> importa as dependências necessárias para o projeto
- Wildfly -> servidor da aplicação
- Mysql -> banco de dados

