# 📊 Comparar Grupos entre Usuários (Excel - Java)

Este projeto tem como objetivo comparar os acessos que dois usuários possuem em arquivos Excel diferentes e gerar um novo arquivo com os acessos que o um usuário X tem a mais em relação a um usuário Y.  
Dessa forma, o usuário Y pode receber os mesmos acessos para padronização de acessos.

---

## 📁 Estrutura do Projeto

```
comparar-acessos-de-usuarios/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── meuapp/
│   │               └── comparargrupos/
│   │                   ├── Main.java        # Classe principal
|   |                   ├── CompararGruposExcel.java   # Lógica de comparação de grupos        
│   │                   └── ExcelUtils.java  # Utilitário para leitura e geração de Excel
│   └── test/
│       └── java/
│           └── com/
│               └── meuapp/
│                   └── comparargrupos/
│                       └── ExcelUtilsTest.java  # Testes unitários JUnit
├── pom.xml           # Configuração Maven
├── README.md         # Documentação do projeto
└── .gitignore
```

---

## ✅ Requisitos

- Java 21 ou superior  
- Maven 3.9+  
- Biblioteca [Apache POI](https://poi.apache.org/) (já configurada no `pom.xml`)  

---

## 📦 Instalação

1. **Clone o repositório**:

```bash
git clone https://github.com/seu-usuario/comparar-acessos-de-usuarios.git
cd comparar-acessos-de-usuarios
```

2. **Compile o projeto**:

```bash
mvn clean install
```

---

## 🛠️ Como usar

1. Execute a aplicação com Maven:

```bash
mvn exec:java -Dexec.mainClass="com.meuapp.comparargrupos.Main"
```

2. Selecione o arquivo Excel do **usuário X**.  
3. Selecione o arquivo Excel do **usuário Y**.  
4. O sistema irá gerar um novo arquivo Excel com os grupos que o usuário X tem a mais em relação ao usuário Y.  

---

## 📋 Formato esperado das planilhas

As planilhas de entrada devem conter as colunas com os seguintes nomes:

- `Login` → nome do usuário  
- `Grupo` → nome do grupo de acesso  

⚠️ O sistema é sensível ao nome exato das colunas!  

---

## 🧾 Exemplo de saída

Será gerado um arquivo com o seguinte nome:

```
grupos_para_<usuarioY>.xlsx
```

Esse arquivo conterá os grupos que o usuário Y ainda não possui.  

---

## 📌 Tecnologias Utilizadas

- Java 21  
- Maven  
- Apache POI (manipulação de arquivos Excel)  
- Swing (interface de seleção de arquivos)  
- JUnit 5 (testes unitários)  

---

## 🧑‍💻 Autor

Desenvolvido por **Filipe C.**  
GitHub: [https://github.com/filipecrodrigues](https://github.com/filipecrodrigues)

---

## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).  
