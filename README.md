# Development Test

Esse projeto consiste em dois plugins para o spigot. O primeiro contém um simples plugin de home e o outro permite a manipulação da força, velocidade e partícula de um Wind Charger.

## Sumário

- [Instalação do projeto](#instalação-do-projeto)
  - [Instalando manualmente](#instalação-manual)
  - [Instalanado com Docker](#instalação-com-docker)
- [Plugin de homes](#homes)
- [Plugin de WindCharger](#windcharger)

## Instalação do projeto

Para instalar o projeto é necessário os seguintes recursos:
- JDK 21+
- Gradle 8.6+
- Docker

Após clonar o repositório pode seguir para a instalação manual ou utilizando o Docker.

### Instalação Manual

O projeto pode ser instalado manualmente construindo as jars dos plugins e enviando os arquivos para o seu próprio servidor. Para isso, é necessário executar o comando na pasta raiz do projeto:
```gradle
gradle shadowJar
```
Após a conclusão, os arquivos estarão presentes nas pastas de build/libs nas pastas de [home](https://github.com/SrBlecaute01/application/tree/main/home) e [windcharger](https://github.com/SrBlecaute01/application/tree/main/windcharge).

**OBS** Para o plugin de home é necessário fazer as configurações do banco de dados em sua configuração.

### Instalação com Docker

O servidor pode ser construído automaticamente com o Docker, sendo necessário apenas executar o comando na pasta raiz do projeto.
```
docker-compose up -d
```
O Docker irá automaticamente construir os plugins, inicializar o banco de dados com o MariaDB e em seguida o servidor. Por definição o servidor se encontrará na porta `25565`

## Homes

O plugin é um simples plugin de homes que permite aos jogadores definir localizações e voltar para elas quando quiserem.

https://github.com/user-attachments/assets/211b8145-7ead-470c-8a4a-dc7ef059a333

| Comandos | Descrição  |
|--|--|
| /homes | Visualiza as homes definidas |
| /home \<home> | Teletransporta para uma home existente
| /sethome \<home> | Cria uma nova home na localização |
| /delhome \<home> | Deleta uma home existente |

As homes são salvas no banco de dados e são somente carregadas quando um player entra no servidor e descarregadas quando ele sai do servidor.

## WindCharger

O plugin permite que tanto a força como a velocidade e a partícula de um Wind Charger possam ser alteradas em sua configuração. Utilizando os eventos de projectile hit, knockback e projectile launch para permitir essas alterações.

https://github.com/user-attachments/assets/e113b813-793c-4006-9b55-ee6dd53fd538
