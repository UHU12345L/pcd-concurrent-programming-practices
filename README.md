# Concurrent and Distributed Programming - Practices

**Course:** Concurrent and Distributed Programming (Programación Concurrente y Distribuida)\
**University:** Universidad de Huelva\
**Year:** 2026-2027

## 📄 Description

Practices of the PCD course, written in Java. Each practice builds on the previous one:
a circular queue implemented in Practice 1 is progressively extended until it becomes a
shared object used by concurrent threads.

## 📁 Repository Structure

| Folder | Practice | Topic |
|--------|----------|-------|
| `Practica1_PCD` | Practice 1 | Circular queue with array |
| `Practica2_PCD` | Practice 2 | Thread creation: producer and consumers |

Each folder contains the NetBeans project and the assignment statement (PDF).

---

## 🔁 Practice 1 - Circular Queue with Array

### Description

First contact with Java and NetBeans. Implementation of a **circular queue** backed by a
fixed-size array: when the tail reaches the end of the array, new elements are stored again
at the beginning, reusing the slots freed by previously removed elements.

### 🎯 Learning Objectives

- Getting started with Java and the NetBeans environment
- Defining a contract with an **interface** and implementing it in a class
- Implementing a circular buffer with modular arithmetic
- Handling error cases (full / empty queue) with exceptions

### 🧩 Classes Implemented

- **ICola**: Interface with the queue operations: `acola`, `desacola`, `primero`, `getNum`
- **Cola**: Circular queue. `head` points to the first element and `tail` to the next free slot; both advance with `(pos + 1) % capacidad`. An element counter distinguishes the full and empty states (in both cases `head == tail`)
- **UsaCola**: Test program: a queue of capacity 4 and a 10-iteration loop that randomly inserts the iteration number or removes the first element

---

## 🧵 Practice 2 - Thread Creation: Producer and Consumers

### Description

Introduction to the two ways of creating threads in Java, using the queue from Practice 1
as a shared object. The queue becomes **ColaLenta** (slow queue): `acola` and `desacola`
sleep 100 ms between every instruction, which widens the window in which threads can
interfere with each other.

A **producer** thread inserts 10 random floats and, once it finishes, **two consumer**
threads extract numbers from the same queue at the same time. Since there is no mutual
exclusion, the consumers produce inconsistent results.

### 🎯 Learning Objectives

- Creating threads by **extending `Thread`** (Productor)
- Creating threads by **implementing `Runnable`** and wrapping them in a `Thread` (Consumidor)
- Starting threads with `start()` and waiting for them with `join()`
- Sharing one object between several threads
- Observing **race conditions** caused by the lack of mutual exclusion

### 🧩 Classes Implemented

- **ColaLenta**: Circular queue from Practice 1 with a `Thread.sleep(100)` between every instruction of `acola` and `desacola`
- **Productor**: Extends `Thread`. Inserts 10 random floats (0-100) and prints the thread id and each number
- **Consumidor**: Implements `Runnable`. Extracts 10 numbers and prints the thread id and each value
- **UsaCola**: Tests ColaLenta with a single thread, then runs one producer, waits for it, and runs two consumers at the same time

### 📊 Results

| Test | Threads | Result |
|------|---------|--------|
| ColaLenta alone | main only | ✅ Correct FIFO order |
| Producer | 1 producer | ✅ 10 numbers inserted correctly |
| Consumers | 2 consumers at once | ❌ Inconsistent: duplicated and lost values |

Example run with the two consumers (thread ids 37 and 38):

```
Identificador del hilo: 38 número extraido: 37.933395
Identificador del hilo: 37 número extraido: 37.933395
Identificador del hilo: 37 número extraido: 50.855762
Identificador del hilo: 38 número extraido: 50.855762
...
```

- Both consumers read the same `head` before either of them advances it → **the same number is extracted twice**
- Both then advance `head` → **the next number is skipped and lost**
- `numelementos` is updated by both at once, so the counter no longer matches the real content: in this run, 12 extractions were made from a queue holding 10 numbers, and 4 of the inserted numbers were never extracted

Results change on every run, depending on how the threads are scheduled.

---

## 💻 Technologies Used

- **Language:** Java
- **IDE:** NetBeans

## 🚀 How to Run

1. Clone the repository
2. In NetBeans, open the project of the practice you want (*File → Open Project*):
   - Practice 1: `Practica1_PCD/P1/Practica1`
   - Practice 2: `Practica2_PCD/P2_PCD`
3. Run `UsaCola.java`
