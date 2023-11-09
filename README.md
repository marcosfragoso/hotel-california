# Hotel Califórnia
## Descrição do projeto
Este projeto implementa um sistema para o Hotel Califórnia, com funcionalidades relacionadas à gestão de reservas, quartos, refeições, formas de pagamento e áreas comuns. O sistema é estruturado em uma arquitetura de camadas, incluindo um Controlador de Fachada, Controladores de Sessão, Repositório de Dados e Entidades de Negócio, onde os controladores de sessão foram implementados usando o conceito do padrão de projeto Singleton para garantir a unicidade dos mesmos.
## Estrutura do projeto
* **br.edu.ufcg.computacao.p2lp2.hotelcalifornia.controller:** Contém as classes dos controladores de sessão.
* **br.edu.ufcg.computacao.p2lp2.hotelcalifornia.entities:** Contém as entidades de negócio.
* **br.edu.ufcg.computacao.p2lp2.hotelcalifornia.exception:** Contém a classe HotelCaliforniaException, uma exceção personalizada para o projeto.
* **HotelCaliforniaSistema:** Contém a classe principal do sistema, representando o Controlador de Fachada.
## Tecnologias utilizadas
* Java 8+
* JUnit 5
* Maven
* Mockito
## Contribuidores
* @ViniciusI4n
* @marcosfragoso
* @EvertonKauan
