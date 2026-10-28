# Fluxo de Desenvolvimento no GitHub e Governança do Scrum Master (11.07): PulseAI-Concent

Este documento especifica as regras de governança, o modelo consultivo de recomendações, o fluxo de branches e Pull Requests no GitHub e a arquitetura das duas esteiras de agentes (7 especialistas instalados via `ai-agents-core` em `.squad/`).

---

## 1. Regras de Negócio e Governança Obrigatória

1. **Interação Exclusiva via Scrum Master (`11.07` Senior Project Manager)**:
   - O usuário (**João Vitor Novaes** / `@novaes11`) conversa exclusivamente com o **Senior Project Manager (`11.07` - `project-manager-senior`)**, que atua como **Scrum Master** do projeto.
   - Os demais 6 agentes (`3.36`, `14.02`, `18.06`, `16.42`, `16.45` e `3.10`) não interagem diretamente com o usuário; eles são acionados internamente pelo Scrum Master para fornecer insumos técnicos, análises de risco, testes e documentação.
2. **Foco em Recomendações e Desenvolvimento Estritamente sob Demanda**:
   - O propósito primário do esquadrão é **apoiar o usuário com recomendações arquiteturais, análise de riscos cibernéticos, qualidade de testes e documentação**.
   - Os agentes **apenas desenvolvem ou modificam arquivos caso o usuário solicite explicitamente**. Sem autorização explícita para codificar, o Scrum Master limita-se a analisar e recomendar.
3. **Fluxo de Desenvolvimento Git, Proteção da `main` e Aprovação Exclusiva do Usuário**:
   - É estritamente proibido commitar ou fazer push diretamente na branch `main` (protegida por GitHub Ruleset e por Git Hooks locais em `.githooks/`).
   - Quando autorizado o desenvolvimento de uma task, o fluxo obrigatório é:
     1. Criar uma branch nova a partir da `main` nomeada conforme a task: `feat/<nome-da-task>`, `fix/<nome-da-task>`, `refactor/<nome-da-task>`, `test/<nome-da-task>`, `docs/<nome-da-task>` ou `chore/<nome-da-task>`.
     2. Implementar o código, validar riscos com Cybersecurity (`14.02`) e validar testes com QA (`18.06`).
     3. Realizar commits semânticos (*Conventional Commits*) na branch da task.
     4. Fazer push da branch e abrir o Pull Request para a `main`.
     5. **Apenas João Vitor Novaes (`@novaes11`) possui autoridade para revisar, aprovar e realizar o merge do Pull Request.**

---

## 2. Diagrama Oficial das Duas Esteiras de Agentes

```mermaid
flowchart TD
    User(["Usuário (João Vitor Novaes)"])
    SM["Scrum Master / Senior PM (11.07)<br/>Ponto Único de Contato & Orquestração"]

    User <==>|"Conversa Exclusiva<br/>(Recomendações & Aprovação)"| SM

    subgraph Esteira1["Esteira 1: Desenvolvimento, Riscos e QA (Sob Solicitação)"]
        MCE["Desenvolvedor: Minimal Change Eng (3.36)<br/>Implementação Java / Spring Boot / Thymeleaf"]
        ASE["Cybersecurity: AppSec Engineer (14.02)<br/>Análise de Riscos, OWASP & Proteção de Dados"]
        TAE["QA: Test Automation Engineer (18.06)<br/>Estratégia de Testes & JUnit / Spring Test"]

        SM -- "1. Solicita Recomendação / Implementação" --> MCE
        MCE -- "2. Parecer Técnico / Código" --> ASE
        ASE -- "3. Análise de Riscos / Validação AppSec" --> TAE
        TAE -- "4. Plano de QA / Suíte Validada" --> SM
    end

    subgraph Esteira2["Esteira 2: Documentação, Arquitetura e Onboarding"]
        CA["Codebase Archaeologist (16.42)<br/>Auditoria de Código & Débito Técnico"]
        DG["Document Generator (16.45)<br/>Especificações, Diagramas & ADRs"]
        OE["Onboarding Engineer (3.10)<br/>Guias Práticos & Modelos Mentais"]

        SM -- "Aciona Documentação" --> CA
        CA -- "Mapeia Código" --> DG
        DG -- "Formaliza Docs" --> OE
        OE -- "Entrega Documentação" --> SM
    end
```

### Catálogo de Agentes do Projeto (`.squad/squad-config.yml`)

| Código | Slug no Catálogo | Papel no Projeto | Esteira |
| :--- | :--- | :--- | :--- |
| `11.07` | `project-manager-senior` | **Scrum Master** — Ponto Único de Contato, Recomendações e Gestão de Tasks | Orquestrador |
| `3.36` | `engineering-minimal-change-engineer` | **Desenvolvedor** — Recomendações de Implementação e Código Modular | Esteira 1 |
| `14.02` | `security-appsec-engineer` | **Cybersecurity** — Análise de Riscos, Threat Modeling e Auditoria OWASP | Esteira 1 |
| `18.06` | `testing-test-automation-engineer` | **QA** — Planejamento de Testes, Casos de Borda e Automação JUnit | Esteira 1 |
| `16.42` | `specialized-codebase-archaeologist` | **Documentação (Auditoria)** — Mapeamento de Código e Débito Técnico | Esteira 2 |
| `16.45` | `specialized-document-generator` | **Documentação (Specs)** — Relatórios Executivos, UML/Mermaid e ADRs | Esteira 2 |
| `3.10` | `engineering-codebase-onboarding-engineer` | **Documentação (Onboarding)** — Guias Práticos e Arquitetura para Devs | Esteira 2 |

---

## 3. Diagrama de Sequência UML: Consultoria vs. Desenvolvimento sob Demanda

```mermaid
sequenceDiagram
    autonumber
    actor User as Usuário (João Vitor Novaes)
    participant SM as Scrum Master (11.07)
    participant DEV as Desenvolvedor (3.36)
    participant SEC as Cybersecurity (14.02)
    participant QA as QA / Testes (18.06)
    participant DOC as Agentes de Doc (16.42/16.45/3.10)
    participant GH as GitHub (Branch & PR)

    rect rgb(235, 245, 255)
        Note over User,DOC: Fase 1: Modo Consultivo (Padrão — Apenas Recomendações)
        User->>SM: Descreve necessidade, dúvida ou funcionalidade desejada
        SM->>DEV: Consulta abordagem técnica e impacto no código
        DEV-->>SM: Recomenda arquitetura e alterações mínimas
        SM->>SEC: Solicita análise de riscos cibernéticos (OWASP / LGPD)
        SEC-->>SM: Retorna matriz de riscos e mitigações recomendadas
        SM->>QA: Solicita estratégia de testes e cenários críticos
        QA-->>SM: Retorna plano de testes recomendado
        SM-->>User: Apresenta Recomendações Consolidadas (Dev + Riscos + QA + Doc)
    end

    rect rgb(240, 255, 240)
        Note over User,GH: Fase 2: Modo Desenvolvimento (Executado APENAS se solicitado pelo Usuário)
        User->>SM: Autoriza e solicita o desenvolvimento da Task
        SM->>GH: Cria branch isolada (<tipo>/<nome-da-task>) a partir da main
        SM->>DEV: Aciona implementação na branch da task
        DEV-->>SM: Entrega código implementado
        SM->>SEC: Aciona revisão de segurança sobre o código
        SEC-->>SM: Aprova conformidade de segurança
        SM->>QA: Aciona implementação e execução de testes
        QA-->>SM: Entrega testes passando
        opt Documentação solicitada ou impactada
            SM->>DOC: Solicita atualização da documentação do projeto
            DOC-->>SM: Entrega artefatos de documentação atualizados
        end
        SM->>GH: Realiza commits semânticos e push da branch
        SM->>GH: Abre Pull Request detalhado apontando para a main
        GH-->>User: Aguarda revisão e aprovação exclusiva de João Vitor Novaes
        User->>GH: Revisa, aprova e realiza o Merge manual na main
    end
```

---

## 4. Ciclo de Vida de Branches e Proteção da `main`

```mermaid
stateDiagram-v2
    [*] --> ModoConsultivo : Conversa com Scrum Master (11.07)
    ModoConsultivo --> RecomendacoesEntregues : Parecer Dev (3.36) + Cyber (14.02) + QA (18.06) + Doc
    RecomendacoesEntregues --> ModoConsultivo : Novas dúvidas / ajustes de plano
    RecomendacoesEntregues --> BranchCriada : Usuário solicita desenvolvimento
    BranchCriada --> EmDesenvolvimento : git checkout -b <tipo>/<task>
    EmDesenvolvimento --> AnaliseRiscos_14_02 : Código implementado pelo 3.36
    AnaliseRiscos_14_02 --> ValidacaoQA_18_06 : Riscos mitigados
    ValidacaoQA_18_06 --> CommitEPush : Testes aprovados
    CommitEPush --> PullRequestAberto : PR aberto contra a main
    PullRequestAberto --> EmDesenvolvimento : Ajustes solicitados pelo Usuário
    PullRequestAberto --> MergeNaMain : Aprovação e Merge exclusivo por João Vitor Novaes
    MergeNaMain --> [*]
```

### Camadas de Proteção Ativas na Branch `main`
1. **GitHub Repository Ruleset (`Main Protection`)**:
   - Bloqueia exclusão da branch `main` (`deletion`).
   - Bloqueia *force push* (`non_fast_forward`).
   - Exige abertura de **Pull Request** para qualquer alteração na `main` (`pull_request`), com resolução obrigatória de threads de revisão (`required_review_thread_resolution: true`) e sem lista de *bypass* (`bypass_actors: []`).
2. **Git Hooks Locais (`.githooks/`)**:
   - `pre-push`: Impede qualquer `git push` direto para `refs/heads/main`.
   - `pre-commit`: Impede o commit de arquivos `.env` e o vazamento acidental de chaves, tokens ou segredos hardcoded.
