## Departamento de Ciˆencia de Computadores FCUP

# Inteligencia Artificial CC2006ˆ 2019/

# Projeto Pratico 1 ́

Vigilˆancia de Partic ̧oes Retangulares ̃

Recordar o problema de vigilancia de partic ̧ˆ oes retangulares abordado nas primeiras aulas pr ̃ aticas. Dada ́

uma partic ̧ ̃aoΠ, considerar a possibilidade de:

- todos os retangulos deˆ Πterem de ser cobertos;
- apenas um subconjuntoΠ

```
′
```
```
⊂Πtem de ser coberto.
```
O trabalho consistir ́a na resoluc ̧ao dos problemas seguintes, devendo entregar as implementac ̧ ̃ oes, um re- ̃

latorio em PDF, com a descric ̧ ́ ao dos m ̃ ́etodos, dos resultados experimentais, e da implementac ̧ao. Os ̃

grupos dever

### ̃

```
ao ainda apresentar oralmente os seus trabalhos. Poder
```
### ̃

```
ao usar Java, C/C++, Python, excepto
```
nas questoes 4c) e 5 (em que as implementac ̧ ̃ oes ter ̃ ao de ser em Prolog+CLP). Nas restantes, tamb ̃ ́em

poderao usar Prolog. ̃

Prazo: 6 de Maio 2020

## 1. Aplicac ̧ ̃ao das estrategias ́ greedydiscutidas nas aulas (ou outras) para obter uma poss ́ıvel colocac ̧ ̃ao dos

guardas que garanta a cobertura pedida.

## 2. Aplicac ̧ao de m ̃ etodos de pesquisa n ́ ̃ao informada e informada para determinar soluc ̧oes exatas: ̃

- breadth-first search(em largura)
- depth-first search(em profundidade)
- iterative deepening search(aprofundamento progressivo)

### • A

```
?
```
```
search
```
- Branch-and-bound(ramificac ̧ao-e-limitac ̧ ̃ ao) ̃

## 3. Aplicac ̧ao de m ̃ etodos de pesquisa local e pesquisa local estoc ́ astica perturbativos ou construtivos ́

- Pesquisa local iterada (com e sem aleatorizac ̧ao) ̃
- Simulated Annealingou umalgoritmo baseado em Colonia de Formigas ́

## 4. An

### ́

```
alise da aplicac ̧
```
### ̃

```
ao de Programac ̧
```
### ̃

```
ao por Restric ̧
```
### ̃

```
oes (CLP).
```
a) Definir o modelo matematico do problema; ́

b)Implementar um programa que resolva o problema sem recorrer a modulos de programac ̧ ́ ao por restric ̧ ̃ oes ̃

mas aplique propagac ̧ao de restric ̧ ̃ oes ( ̃ MACcom AC-3).

### 1


c) Implementar um predicado para resoluc ̧ao, preferencialmente, no sistema ECLiPSE (biblioteca ic e ̃

branchandbound, etc). Em alternativa, se a primeira nao for vi ̃ ́avel, usar SWI Prolog (biblioteca clpfd).

Estudar abordagens distintas para a fase de pesquisa (enumerac ̧ ̃ao).

## 5. Considerar no sistema CLP uma extens

### ̃

```
ao do problema em que se atribui cores aos guardas e se obriga
```
a ter cores distintas para guardas que vejam o mesmo retˆangulo. Modificar o modelo e estudar o problema

usando CLP. Para o numero m ́ ́ınimo de guardas, qual seria o numero m ́ ́ınimo de cores?

### 2


