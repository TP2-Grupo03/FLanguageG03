# Técnicas de Programação 2

> Projeto 1 - Grupo 03

### Integrantes:
* João Davi Ribeiro Tavares Leite - 222001322
* Laryssa de Oliveira Ferreira - 222005386
* Luana Pinho Torres - 222011623
* Lurian Correia Lima - 222005395
* Marcus Emanuel Carvalho Tenedini de Freitas - 222025960
* Tiago Cabral de Faria - 160146712
* Victor de Andrade Arruda - 222001378

### Link do vídeo: (wip)

## Objetivos

Fazer os alunos se tornarem mais confortáveis com a linguagem de programação Scala, a escrita de testes automatizados com a biblioteca ScalaTest, e com os conceitos de Monads e Monad Transformers.


## Executando o Trabalho

### Pré-requisitos

* [Scala](https://www.scala-lang.org/download/) 

Abra o prompt de comando e digite scala -version. Você deve ver a versão do Scala instalada.

* [SBT (Scala Build Tool)]([https://www.scala-sbt.org/download.html](https://www.scala-sbt.org/index.html)): uma ferramenta de build projetada especificamente para projetos em Scala.

* Cats: A biblioteca Cats fornece abstrações para programação funcional em Scala, facilitando o uso de Monads, Functors e outras estruturas.


## Atividade 1
A primeira atividade consiste em revisar a implementação da versão State, passando a usar a definição de Monad State descrita no Capítulo 9 do livro "Scala with Cats".
A branch criada foi chamada de [state-with-cats](https://github.com/TP2-Grupo03/FLanguageG03/tree/state-with-cats).

## Atividade 2
Esta atividade envolveu a implementação de uma nova versão que usa uma stack de monads composta por uma State Monad e uma Error Monad, reutilizando as implementações dessas Monads da biblioteca Cats. A branch criada foi chamada de [state-and-eth-with-cats](https://github.com/TP2-Grupo03/FLanguageG03/tree/state-and-eh-with-cats).

## Atividade 3
Nesta atividade, incluímos o suporte a uma nova expressão IfThenElse na versão State and EH with Cats da branch anterior, também com monad transformers. Além disso, elaboramos uma proposta para representar tipos booleanos na linguagem FLang, alteramos a representação intermediária (AST) e revisamos o interpretador.


## Atividade 4
A última atividade envolveu a definição de uma sintaxe concreta para a linguagem FLang e a implementação de um parser seguindo a estratégia de parser combinators capaz de interpretar a sintaxe corretamente. Ela foi feita em cima da branch da atividade 3.

# Fontes
## Atividade 1 e 2
* https://scalawithcats.com/dist/scala-with-cats.html#the-state-monad
* https://typelevel.org/cats-effect/docs/getting-started
* https://www.youtube.com/watch?v=T8fhAHw9GM0

## Atividade 3 
* https://docs.scala-lang.org/overviews/scala-book/if-then-else-construct.html
* https://docs.scala-lang.org/scala3/book/control-structures.html#the-ifthenelse-construct

## Atividade 4
* https://ruslanspivak.com/lsbasi-part7/
* https://github.com/scala/scala-parser-combinators
* https://github.com/scala/scala-parser-combinators/blob/main/docs/Getting_Started.md
* https://enear.github.io/2016/03/31/parser-combinators/
